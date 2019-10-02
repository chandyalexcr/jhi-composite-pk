package com.gochandy.service.mapper;

import com.gochandy.domain.*;
import com.gochandy.service.dto.BranchOfficeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BranchOffice} and its DTO {@link BranchOfficeDTO}.
 */
@Mapper(componentModel = "spring", uses = {AffiliateMapper.class})
public interface BranchOfficeMapper extends EntityMapper<BranchOfficeDTO, BranchOffice> {

    @Mapping(source = "affiliate.code", target = "affiliateCode")
    @Mapping(source = "branchOfficeId.number", target = "number")
    BranchOfficeDTO toDto(BranchOffice branchOffice);

    @Mapping(source = "affiliateCode", target = "affiliate")
    BranchOffice toEntity(BranchOfficeDTO branchOfficeDTO);

    default BranchOffice fromId(BranchOfficeId branchOfficeId) {
        if (branchOfficeId == null) {
            return null;
        }
        BranchOffice branchOffice = new BranchOffice();
        branchOffice.setBranchOfficeId(branchOfficeId);
        return branchOffice;
    }
}
