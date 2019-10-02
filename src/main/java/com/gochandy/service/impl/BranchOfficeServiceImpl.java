package com.gochandy.service.impl;

import com.gochandy.service.BranchOfficeService;
import com.gochandy.domain.BranchOffice;
import com.gochandy.domain.BranchOfficeId;
import com.gochandy.repository.BranchOfficeRepository;
import com.gochandy.service.dto.BranchOfficeDTO;
import com.gochandy.service.mapper.BranchOfficeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link BranchOffice}.
 */
@Service
@Transactional
public class BranchOfficeServiceImpl implements BranchOfficeService {

    private final Logger log = LoggerFactory.getLogger(BranchOfficeServiceImpl.class);

    private final BranchOfficeRepository branchOfficeRepository;

    private final BranchOfficeMapper branchOfficeMapper;

    public BranchOfficeServiceImpl(BranchOfficeRepository branchOfficeRepository, BranchOfficeMapper branchOfficeMapper) {
        this.branchOfficeRepository = branchOfficeRepository;
        this.branchOfficeMapper = branchOfficeMapper;
    }

    /**
     * Save a branchOffice.
     *
     * @param branchOfficeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BranchOfficeDTO save(BranchOfficeDTO branchOfficeDTO) {
        log.debug("Request to save BranchOffice : {}", branchOfficeDTO);
        BranchOffice branchOffice = branchOfficeMapper.toEntity(branchOfficeDTO);
        branchOffice = branchOfficeRepository.save(branchOffice);
        return branchOfficeMapper.toDto(branchOffice);
    }

    /**
     * Get all the branchOffices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BranchOfficeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BranchOffices");
        return branchOfficeRepository.findAll(pageable)
            .map(branchOfficeMapper::toDto);
    }


    /**
     * Get one branchOffice by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BranchOfficeDTO> findOne(String code, String number) {
        log.debug("Request to get BranchOffice : {}", code + " + " + number);
        BranchOfficeId bId = new BranchOfficeId(code, number);
        return branchOfficeRepository.findById(bId)
            .map(branchOfficeMapper::toDto);
    }

    /**
     * Delete the branchOffice by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String code, String number) {
        log.debug("Request to delete BranchOffice : {}", code + " + " + number);
         BranchOfficeId bId = new BranchOfficeId("", "");
        branchOfficeRepository.deleteById(bId);
    }
}
