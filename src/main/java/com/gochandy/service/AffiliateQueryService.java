package com.gochandy.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.gochandy.domain.Affiliate;
import com.gochandy.domain.*; // for static metamodels
import com.gochandy.repository.AffiliateRepository;
import com.gochandy.service.dto.AffiliateCriteria;
import com.gochandy.service.dto.AffiliateDTO;
import com.gochandy.service.mapper.AffiliateMapper;

/**
 * Service for executing complex queries for {@link Affiliate} entities in the database.
 * The main input is a {@link AffiliateCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AffiliateDTO} or a {@link Page} of {@link AffiliateDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AffiliateQueryService extends QueryService<Affiliate> {

    private final Logger log = LoggerFactory.getLogger(AffiliateQueryService.class);

    private final AffiliateRepository affiliateRepository;

    private final AffiliateMapper affiliateMapper;

    public AffiliateQueryService(AffiliateRepository affiliateRepository, AffiliateMapper affiliateMapper) {
        this.affiliateRepository = affiliateRepository;
        this.affiliateMapper = affiliateMapper;
    }

    /**
     * Return a {@link List} of {@link AffiliateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AffiliateDTO> findByCriteria(AffiliateCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Affiliate> specification = createSpecification(criteria);
        return affiliateMapper.toDto(affiliateRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AffiliateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AffiliateDTO> findByCriteria(AffiliateCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Affiliate> specification = createSpecification(criteria);
        return affiliateRepository.findAll(specification, page)
            .map(affiliateMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AffiliateCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Affiliate> specification = createSpecification(criteria);
        return affiliateRepository.count(specification);
    }

    /**
     * Function to convert {@link AffiliateCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Affiliate> createSpecification(AffiliateCriteria criteria) {
        Specification<Affiliate> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Affiliate_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Affiliate_.name));
            }
        }
        return specification;
    }
}
