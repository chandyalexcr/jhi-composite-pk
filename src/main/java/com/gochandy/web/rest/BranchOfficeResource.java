package com.gochandy.web.rest;

import com.gochandy.service.BranchOfficeService;
import com.gochandy.web.rest.errors.BadRequestAlertException;
import com.gochandy.service.dto.BranchOfficeDTO;
import com.gochandy.service.dto.BranchOfficeCriteria;
import com.gochandy.service.BranchOfficeQueryService;

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
 * REST controller for managing {@link com.gochandy.domain.BranchOffice}.
 */
@RestController
@RequestMapping("/api")
public class BranchOfficeResource {

    private final Logger log = LoggerFactory.getLogger(BranchOfficeResource.class);

    private static final String ENTITY_NAME = "branchOffice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BranchOfficeService branchOfficeService;

    private final BranchOfficeQueryService branchOfficeQueryService;

    public BranchOfficeResource(BranchOfficeService branchOfficeService, BranchOfficeQueryService branchOfficeQueryService) {
        this.branchOfficeService = branchOfficeService;
        this.branchOfficeQueryService = branchOfficeQueryService;
    }

    /**
     * {@code POST  /branch-offices} : Create a new branchOffice.
     *
     * @param branchOfficeDTO the branchOfficeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new branchOfficeDTO, or with status {@code 400 (Bad Request)} if the branchOffice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/branch-offices")
    public ResponseEntity<BranchOfficeDTO> createBranchOffice(@Valid @RequestBody BranchOfficeDTO branchOfficeDTO) throws URISyntaxException {
        log.debug("REST request to save BranchOffice : {}", branchOfficeDTO);
        if (branchOfficeDTO.getNumber() != null) {
            throw new BadRequestAlertException("A new branchOffice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BranchOfficeDTO result = branchOfficeService.save(branchOfficeDTO);
        return ResponseEntity.created(new URI("/api/branch-offices/" + result.getNumber()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getNumber()))
            .body(result);
    }

    /**
     * {@code PUT  /branch-offices} : Updates an existing branchOffice.
     *
     * @param branchOfficeDTO the branchOfficeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated branchOfficeDTO,
     * or with status {@code 400 (Bad Request)} if the branchOfficeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the branchOfficeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/branch-offices")
    public ResponseEntity<BranchOfficeDTO> updateBranchOffice(@Valid @RequestBody BranchOfficeDTO branchOfficeDTO) throws URISyntaxException {
        log.debug("REST request to update BranchOffice : {}", branchOfficeDTO);
        if (branchOfficeDTO.getNumber() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BranchOfficeDTO result = branchOfficeService.save(branchOfficeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, branchOfficeDTO.getNumber()))
            .body(result);
    }

    /**
     * {@code GET  /branch-offices} : get all the branchOffices.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of branchOffices in body.
     */
    @GetMapping("/branch-offices")
    public ResponseEntity<List<BranchOfficeDTO>> getAllBranchOffices(BranchOfficeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get BranchOffices by criteria: {}", criteria);
        Page<BranchOfficeDTO> page = branchOfficeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /branch-offices/count} : count all the branchOffices.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/branch-offices/count")
    public ResponseEntity<Long> countBranchOffices(BranchOfficeCriteria criteria) {
        log.debug("REST request to count BranchOffices by criteria: {}", criteria);
        return ResponseEntity.ok().body(branchOfficeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /branch-offices/:id} : get the "id" branchOffice.
     *
     * @param id the id of the branchOfficeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the branchOfficeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/branch-offices/{code}/{number}")
    public ResponseEntity<BranchOfficeDTO> getBranchOffice(@PathVariable String code, @PathVariable String number) {
        log.debug("REST request to get BranchOffice : {}", code + " + " + number);
        Optional<BranchOfficeDTO> branchOfficeDTO = branchOfficeService.findOne(code, number);
        return ResponseUtil.wrapOrNotFound(branchOfficeDTO);
    }

    /**
     * {@code DELETE  /branch-offices/:id} : delete the "id" branchOffice.
     *
     * @param id the id of the branchOfficeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/branch-offices/{code}/{number}")
    public ResponseEntity<Void> deleteBranchOffice(@PathVariable String code, @PathVariable String number) {
        log.debug("REST request to delete BranchOffice : {}", code + " + " + number);
        branchOfficeService.delete(code, number);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, code + " + " + number)).build();
    }
}
