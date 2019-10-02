package com.gochandy.service.impl;

import com.gochandy.service.AffiliateService;
import com.gochandy.domain.Affiliate;
import com.gochandy.repository.AffiliateRepository;
import com.gochandy.service.dto.AffiliateDTO;
import com.gochandy.service.mapper.AffiliateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Affiliate}.
 */
@Service
@Transactional
public class AffiliateServiceImpl implements AffiliateService {

    private final Logger log = LoggerFactory.getLogger(AffiliateServiceImpl.class);

    private final AffiliateRepository affiliateRepository;

    private final AffiliateMapper affiliateMapper;

    public AffiliateServiceImpl(AffiliateRepository affiliateRepository, AffiliateMapper affiliateMapper) {
        this.affiliateRepository = affiliateRepository;
        this.affiliateMapper = affiliateMapper;
    }

    /**
     * Save a affiliate.
     *
     * @param affiliateDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AffiliateDTO save(AffiliateDTO affiliateDTO) {
        log.debug("Request to save Affiliate : {}", affiliateDTO);
        Affiliate affiliate = affiliateMapper.toEntity(affiliateDTO);
        affiliate = affiliateRepository.save(affiliate);
        return affiliateMapper.toDto(affiliate);
    }

    /**
     * Get all the affiliates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AffiliateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Affiliates");
        return affiliateRepository.findAll(pageable)
            .map(affiliateMapper::toDto);
    }


    /**
     * Get one affiliate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AffiliateDTO> findOne(String id) {
        log.debug("Request to get Affiliate : {}", id);
        return affiliateRepository.findById(id)
            .map(affiliateMapper::toDto);
    }

    /**
     * Delete the affiliate by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Affiliate : {}", id);
        affiliateRepository.deleteById(id);
    }
}
