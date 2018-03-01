package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.ContactApp;

import com.mycompany.myapp.domain.Personne;
import com.mycompany.myapp.repository.PersonneRepository;
import com.mycompany.myapp.service.PersonneService;
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

/**
 * Test class for the PersonneResource REST controller.
 *
 * @see PersonneResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ContactApp.class)
public class PersonneResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_NUM_RPPS = "AAAAAAAAAA";
    private static final String UPDATED_NUM_RPPS = "BBBBBBBBBB";

    @Autowired
    private PersonneRepository personneRepository;

    @Autowired
    private PersonneService personneService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPersonneMockMvc;

    private Personne personne;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PersonneResource personneResource = new PersonneResource(personneService);
        this.restPersonneMockMvc = MockMvcBuilders.standaloneSetup(personneResource)
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
    public static Personne createEntity(EntityManager em) {
        Personne personne = new Personne()
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .numRpps(DEFAULT_NUM_RPPS);
        return personne;
    }

    @Before
    public void initTest() {
        personne = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonne() throws Exception {
        int databaseSizeBeforeCreate = personneRepository.findAll().size();

        // Create the Personne
        restPersonneMockMvc.perform(post("/api/personnes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personne)))
            .andExpect(status().isCreated());

        // Validate the Personne in the database
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeCreate + 1);
        Personne testPersonne = personneList.get(personneList.size() - 1);
        assertThat(testPersonne.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testPersonne.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testPersonne.getNumRpps()).isEqualTo(DEFAULT_NUM_RPPS);
    }

    @Test
    @Transactional
    public void createPersonneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personneRepository.findAll().size();

        // Create the Personne with an existing ID
        personne.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonneMockMvc.perform(post("/api/personnes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personne)))
            .andExpect(status().isBadRequest());

        // Validate the Personne in the database
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonnes() throws Exception {
        // Initialize the database
        personneRepository.saveAndFlush(personne);

        // Get all the personneList
        restPersonneMockMvc.perform(get("/api/personnes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personne.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
            .andExpect(jsonPath("$.[*].numRpps").value(hasItem(DEFAULT_NUM_RPPS.toString())));
    }

    @Test
    @Transactional
    public void getPersonne() throws Exception {
        // Initialize the database
        personneRepository.saveAndFlush(personne);

        // Get the personne
        restPersonneMockMvc.perform(get("/api/personnes/{id}", personne.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personne.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()))
            .andExpect(jsonPath("$.numRpps").value(DEFAULT_NUM_RPPS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonne() throws Exception {
        // Get the personne
        restPersonneMockMvc.perform(get("/api/personnes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonne() throws Exception {
        // Initialize the database
        personneService.save(personne);

        int databaseSizeBeforeUpdate = personneRepository.findAll().size();

        // Update the personne
        Personne updatedPersonne = personneRepository.findOne(personne.getId());
        // Disconnect from session so that the updates on updatedPersonne are not directly saved in db
        em.detach(updatedPersonne);
        updatedPersonne
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .numRpps(UPDATED_NUM_RPPS);

        restPersonneMockMvc.perform(put("/api/personnes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonne)))
            .andExpect(status().isOk());

        // Validate the Personne in the database
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeUpdate);
        Personne testPersonne = personneList.get(personneList.size() - 1);
        assertThat(testPersonne.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testPersonne.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testPersonne.getNumRpps()).isEqualTo(UPDATED_NUM_RPPS);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonne() throws Exception {
        int databaseSizeBeforeUpdate = personneRepository.findAll().size();

        // Create the Personne

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonneMockMvc.perform(put("/api/personnes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personne)))
            .andExpect(status().isCreated());

        // Validate the Personne in the database
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonne() throws Exception {
        // Initialize the database
        personneService.save(personne);

        int databaseSizeBeforeDelete = personneRepository.findAll().size();

        // Get the personne
        restPersonneMockMvc.perform(delete("/api/personnes/{id}", personne.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Personne.class);
        Personne personne1 = new Personne();
        personne1.setId(1L);
        Personne personne2 = new Personne();
        personne2.setId(personne1.getId());
        assertThat(personne1).isEqualTo(personne2);
        personne2.setId(2L);
        assertThat(personne1).isNotEqualTo(personne2);
        personne1.setId(null);
        assertThat(personne1).isNotEqualTo(personne2);
    }
}
