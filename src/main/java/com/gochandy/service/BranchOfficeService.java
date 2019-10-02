package com.gochandy.service;

import com.gochandy.service.dto.BranchOfficeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.gochandy.domain.BranchOffice}.
 */
public interface BranchOfficeService {

    /**
     * Save a branchOffice.
     *
     * @param branchOfficeDTO the entity to save.
     * @return the persisted entity.
     */
    BranchOfficeDTO save(BranchOfficeDTO branchOfficeDTO);

    /**
     * Get all the branchOffices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BranchOfficeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" branchOffice.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BranchOfficeDTO> findOne(String code, String number);

    /**
     * Delete the "id" branchOffice.
     *
     * @param id the id of the entity.
     */
    void delete(String code, String number);
}
