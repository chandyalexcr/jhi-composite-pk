package com.gochandy.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A BranchOffice.
 */
@Entity
@Table(name = "branchoffice")
public class BranchOffice implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private BranchOfficeId branchOfficeId;

    @NotNull
    @Size(max = 30)
    @Column(name = "bo_name", length = 30, nullable = false)
    private String name;

    @NotNull
    @Size(max = 1)
    @Column(name = "bo_status", length = 1, nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "bo_code", insertable = false, updatable = false)
    private Affiliate affiliate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public BranchOfficeId getBranchOfficeId() {
        return branchOfficeId;
    }

    public BranchOffice branchOfficeId(BranchOfficeId branchOfficeId) {
        this.branchOfficeId = branchOfficeId;
        return this;
    }

    public void setBranchOfficeId(BranchOfficeId branchOfficeId) {
        this.branchOfficeId = branchOfficeId;
    }
    
    public String getName() {
        return name;
    }

    public BranchOffice name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public BranchOffice status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Affiliate getAffiliate() {
        return affiliate;
    }

    public BranchOffice affiliate(Affiliate affiliate) {
        this.affiliate = affiliate;
        return this;
    }

    public void setAffiliate(Affiliate affiliate) {
        this.affiliate = affiliate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BranchOffice)) {
            return false;
        }
        return branchOfficeId != null && branchOfficeId.equals(((BranchOffice) o).branchOfficeId);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BranchOffice{" +
            "affiliateCode='" + getBranchOfficeId().getAffiliateCode() + "'" +
            ", number='" + getBranchOfficeId().getNumber() + "'" +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
