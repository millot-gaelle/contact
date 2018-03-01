package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.ContactApp;

import com.mycompany.myapp.domain.Telephone;
import com.mycompany.myapp.repository.TelephoneRepository;
import com.mycompany.myapp.service.TelephoneService;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.domain.enumeration.Type;
/**
 * Test class for the TelephoneResource REST controller.
 *
 * @see TelephoneResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ContactApp.class)
public class TelephoneResourceIntTest {

    private static final Type DEFAULT_TYPE = Type.FIXE;
    private static final Type UPDATED_TYPE = Type.PORTABLE;

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    @Autowired
    private TelephoneRepository telephoneRepository;

    @Autowired
    private TelephoneService telephoneService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTelephoneMockMvc;

    private Telephone telephone;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TelephoneResource telephoneResource = new TelephoneResource(telephoneService);
        this.restTelephoneMockMvc = MockMvcBuilders.standaloneSetup(telephoneResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Telephone createEntity(EntityManager em) {
        Telephone telephone = new Telephone()
            .type(DEFAULT_TYPE)
            .numero(DEFAULT_NUMERO);
        return telephone;
    }

    @Before
    public void initTest() {
        telephone = createEntity(em);
    }

    @Test
    @Transactional
    public void createTelephone() throws Exception {
        int databaseSizeBeforeCreate = telephoneRepository.findAll().size();

        // Create the Telephone
        restTelephoneMockMvc.perform(post("/api/telephones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(telephone)))
            .andExpect(status().isCreated());

        // Validate the Telephone in the database
        List<Telephone> telephoneList = telephoneRepository.findAll();
        assertThat(telephoneList).hasSize(databaseSizeBeforeCreate + 1);
        Telephone testTelephone = telephoneList.get(telephoneList.size() - 1);
        assertThat(testTelephone.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTelephone.getNumero()).isEqualTo(DEFAULT_NUMERO);
    }

    @Test
    @Transactional
    public void createTelephoneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = telephoneRepository.findAll().size();

        // Create the Telephone with an existing ID
        telephone.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTelephoneMockMvc.perform(post("/api/telephones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(telephone)))
            .andExpect(status().isBadRequest());

        // Validate the Telephone in the database
        List<Telephone> telephoneList = telephoneRepository.findAll();
        assertThat(telephoneList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = telephoneRepository.findAll().size();
        // set the field null
        telephone.setType(null);

        // Create the Telephone, which fails.

        restTelephoneMockMvc.perform(post("/api/telephones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(telephone)))
            .andExpect(status().isBadRequest());

        List<Telephone> telephoneList = telephoneRepository.findAll();
        assertThat(telephoneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTelephones() throws Exception {
        // Initialize the database
        telephoneRepository.saveAndFlush(telephone);

        // Get all the telephoneList
        restTelephoneMockMvc.perform(get("/api/telephones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(telephone.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.toString())));
    }

    @Test
    @Transactional
    public void getTelephone() throws Exception {
        // Initialize the database
        telephoneRepository.saveAndFlush(telephone);

        // Get the telephone
        restTelephoneMockMvc.perform(get("/api/telephones/{id}", telephone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(telephone.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTelephone() throws Exception {
        // Get the telephone
        restTelephoneMockMvc.perform(get("/api/telephones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTelephone() throws Exception {
        // Initialize the database
        telephoneService.save(telephone);

        int databaseSizeBeforeUpdate = telephoneRepository.findAll().size();

        // Update the telephone
        Telephone updatedTelephone = telephoneRepository.findOne(telephone.getId());
        // Disconnect from session so that the updates on updatedTelephone are not directly saved in db
        em.detach(updatedTelephone);
        updatedTelephone
            .type(UPDATED_TYPE)
            .numero(UPDATED_NUMERO);

        restTelephoneMockMvc.perform(put("/api/telephones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTelephone)))
            .andExpect(status().isOk());

        // Validate the Telephone in the database
        List<Telephone> telephoneList = telephoneRepository.findAll();
        assertThat(telephoneList).hasSize(databaseSizeBeforeUpdate);
        Telephone testTelephone = telephoneList.get(telephoneList.size() - 1);
        assertThat(testTelephone.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTelephone.getNumero()).isEqualTo(UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void updateNonExistingTelephone() throws Exception {
        int databaseSizeBeforeUpdate = telephoneRepository.findAll().size();

        // Create the Telephone

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTelephoneMockMvc.perform(put("/api/telephones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(telephone)))
            .andExpect(status().isCreated());

        // Validate the Telephone in the database
        List<Telephone> telephoneList = telephoneRepository.findAll();
        assertThat(telephoneList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTelephone() throws Exception {
        // Initialize the database
        telephoneService.save(telephone);

        int databaseSizeBeforeDelete = telephoneRepository.findAll().size();

        // Get the telephone
        restTelephoneMockMvc.perform(delete("/api/telephones/{id}", telephone.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Telephone> telephoneList = telephoneRepository.findAll();
        assertThat(telephoneList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Telephone.class);
        Telephone telephone1 = new Telephone();
        telephone1.setId(1L);
        Telephone telephone2 = new Telephone();
        telephone2.setId(telephone1.getId());
        assertThat(telephone1).isEqualTo(telephone2);
        telephone2.setId(2L);
        assertThat(telephone1).isNotEqualTo(telephone2);
        telephone1.setId(null);
        assertThat(telephone1).isNotEqualTo(telephone2);
    }
}
