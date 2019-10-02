package com.gochandy.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.gochandy.domain.Affiliate} entity.
 */
public class AffiliateDTO implements Serializable {

    @NotNull
    @Size(max = 5)
    private String code;

    @NotNull
    @Size(max = 30)
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AffiliateDTO affiliateDTO = (AffiliateDTO) o;
        if (affiliateDTO.getCode() == null || getCode() == null) {
            return false;
        }
        return Objects.equals(getCode(), affiliateDTO.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCode());
    }

    @Override
    public String toString() {
        return "AffiliateDTO{" +
            "code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
