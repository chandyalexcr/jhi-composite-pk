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

import com.gochandy.domain.BranchOffice;
import com.gochandy.domain.*; // for static metamodels
import com.gochandy.repository.BranchOfficeRepository;
import com.gochandy.service.dto.BranchOfficeCriteria;
import com.gochandy.service.dto.BranchOfficeDTO;
import com.gochandy.service.mapper.BranchOfficeMapper;

/**
 * Service for executing complex queries for {@link BranchOffice} entities in the database.
 * The main input is a {@link BranchOfficeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BranchOfficeDTO} or a {@link Page} of {@link BranchOfficeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BranchOfficeQueryService extends QueryService<BranchOffice> {

    private final Logger log = LoggerFactory.getLogger(BranchOfficeQueryService.class);

    private final BranchOfficeRepository branchOfficeRepository;

    private final BranchOfficeMapper branchOfficeMapper;

    public BranchOfficeQueryService(BranchOfficeRepository branchOfficeRepository, BranchOfficeMapper branchOfficeMapper) {
        this.branchOfficeRepository = branchOfficeRepository;
        this.branchOfficeMapper = branchOfficeMapper;
    }

    /**
     * Return a {@link List} of {@link BranchOfficeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BranchOfficeDTO> findByCriteria(BranchOfficeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BranchOffice> specification = createSpecification(criteria);
        return branchOfficeMapper.toDto(branchOfficeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BranchOfficeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BranchOfficeDTO> findByCriteria(BranchOfficeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BranchOffice> specification = createSpecification(criteria);
        return branchOfficeRepository.findAll(specification, page)
            .map(branchOfficeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BranchOfficeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BranchOffice> specification = createSpecification(criteria);
        return branchOfficeRepository.count(specification);
    }

    /**
     * Function to convert {@link BranchOfficeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BranchOffice> createSpecification(BranchOfficeCriteria criteria) {
        Specification<BranchOffice> specification = Specification.where(null);
        if (criteria != null) {
            /*if (criteria.getNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumber(), BranchOffice_.number));
            }*/
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), BranchOffice_.name));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), BranchOffice_.status));
            }
            if (criteria.getAffiliateCode() != null) {
                specification = specification.and(buildSpecification(criteria.getAffiliateCode(),
                    root -> root.join(BranchOffice_.affiliate, JoinType.LEFT).get(Affiliate_.code)));
            }
        }
        return specification;
    }
}
