package com.gochandy.service.mapper;

import com.gochandy.domain.*;
import com.gochandy.service.dto.AffiliateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Affiliate} and its DTO {@link AffiliateDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AffiliateMapper extends EntityMapper<AffiliateDTO, Affiliate> {

    default Affiliate fromId(String code) {
        if (code == null) {
            return null;
        }
        Affiliate affiliate = new Affiliate();
        affiliate.setCode(code);
        return affiliate;
    }
}
