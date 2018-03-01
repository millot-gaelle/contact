package com.mycompany.myapp.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.Type;

/**
 * not an ignored comment
 */
@ApiModel(description = "not an ignored comment")
@Entity
@Table(name = "telephone")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Telephone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type", nullable = false)
    private Type type;

    @Column(name = "numero")
    private String numero;

    @ManyToOne
    private Personne personne;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public Telephone type(Type type) {
        this.type = type;
        return this;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getNumero() {
        return numero;
    }

    public Telephone numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Personne getPersonne() {
        return personne;
    }

    public Telephone personne(Personne personne) {
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
        Telephone telephone = (Telephone) o;
        if (telephone.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), telephone.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Telephone{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", numero='" + getNumero() + "'" +
            "}";
    }
}
