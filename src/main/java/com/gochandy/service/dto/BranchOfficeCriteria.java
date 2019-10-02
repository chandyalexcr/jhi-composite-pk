package com.gochandy.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.gochandy.domain.BranchOffice} entity. This class is used
 * in {@link com.gochandy.web.rest.BranchOfficeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /branch-offices?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BranchOfficeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private StringFilter number;

    private StringFilter name;

    private StringFilter status;

    private StringFilter affiliateCode;

    public BranchOfficeCriteria(){
    }

    public BranchOfficeCriteria(BranchOfficeCriteria other){
        this.number = other.number == null ? null : other.number.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.affiliateCode = other.affiliateCode == null ? null : other.affiliateCode.copy();
    }

    @Override
    public BranchOfficeCriteria copy() {
        return new BranchOfficeCriteria(this);
    }

    public StringFilter getNumber() {
        return number;
    }

    public void setNumber(StringFilter number) {
        this.number = number;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getStatus() {
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public StringFilter getAffiliateCode() {
        return affiliateCode;
    }

    public void setAffiliateCode(StringFilter affiliateCode) {
        this.affiliateCode = affiliateCode;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BranchOfficeCriteria that = (BranchOfficeCriteria) o;
        return
            Objects.equals(number, that.number) &&
            Objects.equals(name, that.name) &&
            Objects.equals(status, that.status) &&
            Objects.equals(affiliateCode, that.affiliateCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        number,
        name,
        status,
        affiliateCode
        );
    }

    @Override
    public String toString() {
        return "BranchOfficeCriteria{" +
                (number != null ? "number=" + number + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (affiliateCode != null ? "affiliateId=" + affiliateCode + ", " : "") +
            "}";
    }

}
