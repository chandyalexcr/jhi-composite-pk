package com.gochandy.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Affiliate.
 */
@Entity
@Table(name = "affiliate")
public class Affiliate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Size(max = 5)
    @Column(name = "a_code", length = 5, nullable = false, unique = true)
    private String code;

    @NotNull
    @Size(max = 30)
    @Column(name = "a_name", length = 30, nullable = false)
    private String name;

    public String getCode() {
        return code;
    }

    public Affiliate code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Affiliate name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Affiliate)) {
            return false;
        }
        return code != null && code.equals(((Affiliate) o).code);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Affiliate{" +
            "code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
