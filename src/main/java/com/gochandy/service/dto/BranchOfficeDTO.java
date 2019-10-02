package com.gochandy.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.gochandy.domain.BranchOffice} entity.
 */
public class BranchOfficeDTO implements Serializable {

    @NotNull
    @Size(max = 4)
    private String number;

    @NotNull
    @Size(max = 30)
    private String name;

    @NotNull
    @Size(max = 1)
    private String status;

    private String affiliateCode;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAffiliateCode() {
        return affiliateCode;
    }

    public void setAffiliateCode(String affiliateCode) {
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

        BranchOfficeDTO branchOfficeDTO = (BranchOfficeDTO) o;
        if (branchOfficeDTO.getNumber()== null || getNumber() == null) {
            return false;
        }
        return Objects.equals(getNumber(), branchOfficeDTO.getNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getNumber());
    }

    @Override
    public String toString() {
        return "BranchOfficeDTO{" +
            "number='" + getNumber() + "'" +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            ", affiliate='" + getAffiliateCode() + "'" +
            "}";
    }
}
