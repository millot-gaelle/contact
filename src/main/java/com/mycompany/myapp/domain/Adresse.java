package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Adresse.
 */
@Entity
@Table(name = "adresse")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Adresse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "compl_num")
    private String complNum;

    @Column(name = "rue")
    private String rue;

    @Column(name = "code_postal")
    private String codePostal;

    @Column(name = "ville")
    private String ville;

    @Column(name = "pays")
    private String pays;

    @ManyToOne
    private Personne personne;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumero() {
        return numero;
    }

    public Adresse numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getComplNum() {
        return complNum;
    }

    public Adresse complNum(String complNum) {
        this.complNum = complNum;
        return this;
    }

    public void setComplNum(String complNum) {
        this.complNum = complNum;
    }

    public String getRue() {
        return rue;
    }

    public Adresse rue(String rue) {
        this.rue = rue;
        return this;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public Adresse codePostal(String codePostal) {
        this.codePostal = codePostal;
        return this;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public Adresse ville(String ville) {
        this.ville = ville;
        return this;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getPays() {
        return pays;
    }

    public Adresse pays(String pays) {
        this.pays = pays;
        return this;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public Personne getPersonne() {
        return personne;
    }

    public Adresse personne(Personne personne) {
        this.personne = personne;
        return this;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Adresse adresse = (Adresse) o;
        if (adresse.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adresse.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Adresse{" +
            "id=" + getId() +
            ", numero=" + getNumero() +
            ", complNum='" + getComplNum() + "'" +
            ", rue='" + getRue() + "'" +
            ", codePostal='" + getCodePostal() + "'" +
            ", ville='" + getVille() + "'" +
            ", pays='" + getPays() + "'" +
            "}";
    }
}
