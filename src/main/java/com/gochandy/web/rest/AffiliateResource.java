package com.gochandy.web.rest;

import com.gochandy.service.AffiliateService;
import com.gochandy.web.rest.errors.BadRequestAlertException;
import com.gochandy.service.dto.AffiliateDTO;
import com.gochandy.service.dto.AffiliateCriteria;
import com.gochandy.service.AffiliateQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.gochandy.domain.Affiliate}.
 */
@RestController
@RequestMapping("/api")
public class AffiliateResource {

    private final Logger log = LoggerFactory.getLogger(AffiliateResource.class);

    private static final String ENTITY_NAME = "affiliate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AffiliateService affiliateService;

    private final AffiliateQueryService affiliateQueryService;

    public AffiliateResource(AffiliateService affiliateService, AffiliateQueryService affiliateQueryService) {
        this.affiliateService = affiliateService;
        this.affiliateQueryService = affiliateQueryService;
    }

    /**
     * {@code POST  /affiliates} : Create a new affiliate.
     *
     * @param affiliateDTO the affiliateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new affiliateDTO, or with status {@code 400 (Bad Request)} if the affiliate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/affiliates")
    public ResponseEntity<AffiliateDTO> createAffiliate(@Valid @RequestBody AffiliateDTO affiliateDTO) throws URISyntaxException {
        log.debug("REST request to save Affiliate : {}", affiliateDTO);
        if (affiliateDTO.getCode() != null) {
            throw new BadRequestAlertException("A new affiliate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AffiliateDTO result = affiliateService.save(affiliateDTO);
        return ResponseEntity.created(new URI("/api/affiliates/" + result.getCode()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getCode()))
            .body(result);
    }

    /**
     * {@code PUT  /affiliates} : Updates an existing affiliate.
     *
     * @param affiliateDTO the affiliateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated affiliateDTO,
     * or with status {@code 400 (Bad Request)} if the affiliateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the affiliateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/affiliates")
    public ResponseEntity<AffiliateDTO> updateAffiliate(@Valid @RequestBody AffiliateDTO affiliateDTO) throws URISyntaxException {
        log.debug("REST request to update Affiliate : {}", affiliateDTO);
        if (affiliateDTO.getCode() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AffiliateDTO result = affiliateService.save(affiliateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, affiliateDTO.getCode()))
            .body(result);
    }

    /**
     * {@code GET  /affiliates} : get all the affiliates.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of affiliates in body.
     */
    @GetMapping("/affiliates")
    public ResponseEntity<List<AffiliateDTO>> getAllAffiliates(AffiliateCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Affiliates by criteria: {}", criteria);
        Page<AffiliateDTO> page = affiliateQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /affiliates/count} : count all the affiliates.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/affiliates/count")
    public ResponseEntity<Long> countAffiliates(AffiliateCriteria criteria) {
        log.debug("REST request to count Affiliates by criteria: {}", criteria);
        return ResponseEntity.ok().body(affiliateQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /affiliates/:id} : get the "id" affiliate.
     *
     * @param id the id of the affiliateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the affiliateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/affiliates/{code}")
    public ResponseEntity<AffiliateDTO> getAffiliate(@PathVariable String code) {
        log.debug("REST request to get Affiliate : {}", code);
        Optional<AffiliateDTO> affiliateDTO = affiliateService.findOne(code);
        return ResponseUtil.wrapOrNotFound(affiliateDTO);
    }

    /**
     * {@code DELETE  /affiliates/:id} : delete the "id" affiliate.
     *
     * @param id the id of the affiliateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/affiliates/{code}")
    public ResponseEntity<Void> deleteAffiliate(@PathVariable String code) {
        log.debug("REST request to delete Affiliate : {}", code);
        affiliateService.delete(code);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, code)).build();
    }
}
