package com.gochandy.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BranchOfficeId implements Serializable {
    
    @Column(name = "bo_code")
    private String affiliateCode;
    
    @Column(name = "bo_num")
    private String number;

    public BranchOfficeId() {
    }

    public BranchOfficeId(String affiliateCode, String number) {
        this.affiliateCode = affiliateCode;
        this.number = number;
    }

    public String getAffiliateCode() {
        return affiliateCode;
    }

    public void setAffiliateCode(String affiliateCode) {
        this.affiliateCode = affiliateCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        final BranchOfficeId other = (BranchOfficeId) o;
        if (!Objects.equals(this.affiliateCode, other.affiliateCode)) {
            return false;
        }
        if (!Objects.equals(this.number, other.number)) {
            return false;
        }
        return true;
    }
    
}
