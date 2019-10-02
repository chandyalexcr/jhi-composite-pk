package com.gochandy.web.rest;

import com.gochandy.TestJhiApp;
import com.gochandy.domain.BranchOffice;
import com.gochandy.domain.Affiliate;
import com.gochandy.repository.BranchOfficeRepository;
import com.gochandy.service.BranchOfficeService;
import com.gochandy.service.dto.BranchOfficeDTO;
import com.gochandy.service.mapper.BranchOfficeMapper;
import com.gochandy.web.rest.errors.ExceptionTranslator;
import com.gochandy.service.dto.BranchOfficeCriteria;
import com.gochandy.service.BranchOfficeQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.gochandy.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BranchOfficeResource} REST controller.
 */
@SpringBootTest(classes = TestJhiApp.class)
public class BranchOfficeResourceIT {

    /* private static final String DEFAULT_NUMBER = "AAAA";
    private static final String UPDATED_NUMBER = "BBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "A";
    private static final String UPDATED_STATUS = "B";

    @Autowired
    private BranchOfficeRepository branchOfficeRepository;

    @Autowired
    private BranchOfficeMapper branchOfficeMapper;

    @Autowired
    private BranchOfficeService branchOfficeService;

    @Autowired
    private BranchOfficeQueryService branchOfficeQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restBranchOfficeMockMvc;

    private BranchOffice branchOffice;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BranchOfficeResource branchOfficeResource = new BranchOfficeResource(branchOfficeService, branchOfficeQueryService);
        this.restBranchOfficeMockMvc = MockMvcBuilders.standaloneSetup(branchOfficeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BranchOffice createEntity(EntityManager em) {
        BranchOffice branchOffice = new BranchOffice()
            .number(DEFAULT_NUMBER)
            .name(DEFAULT_NAME)
            .status(DEFAULT_STATUS);
        return branchOffice;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     *
    public static BranchOffice createUpdatedEntity(EntityManager em) {
        BranchOffice branchOffice = new BranchOffice()
            .number(UPDATED_NUMBER)
            .name(UPDATED_NAME)
            .status(UPDATED_STATUS);
        return branchOffice;
    }

    @BeforeEach
    public void initTest() {
        branchOffice = createEntity(em);
    }

    @Test
    @Transactional
    public void createBranchOffice() throws Exception {
        int databaseSizeBeforeCreate = branchOfficeRepository.findAll().size();

        // Create the BranchOffice
        BranchOfficeDTO branchOfficeDTO = branchOfficeMapper.toDto(branchOffice);
        restBranchOfficeMockMvc.perform(post("/api/branch-offices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(branchOfficeDTO)))
            .andExpect(status().isCreated());

        // Validate the BranchOffice in the database
        List<BranchOffice> branchOfficeList = branchOfficeRepository.findAll();
        assertThat(branchOfficeList).hasSize(databaseSizeBeforeCreate + 1);
        BranchOffice testBranchOffice = branchOfficeList.get(branchOfficeList.size() - 1);
        assertThat(testBranchOffice.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testBranchOffice.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBranchOffice.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createBranchOfficeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = branchOfficeRepository.findAll().size();

        // Create the BranchOffice with an existing ID
        branchOffice.setId(1L);
        BranchOfficeDTO branchOfficeDTO = branchOfficeMapper.toDto(branchOffice);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBranchOfficeMockMvc.perform(post("/api/branch-offices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(branchOfficeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BranchOffice in the database
        List<BranchOffice> branchOfficeList = branchOfficeRepository.findAll();
        assertThat(branchOfficeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = branchOfficeRepository.findAll().size();
        // set the field null
        branchOffice.setNumber(null);

        // Create the BranchOffice, which fails.
        BranchOfficeDTO branchOfficeDTO = branchOfficeMapper.toDto(branchOffice);

        restBranchOfficeMockMvc.perform(post("/api/branch-offices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(branchOfficeDTO)))
            .andExpect(status().isBadRequest());

        List<BranchOffice> branchOfficeList = branchOfficeRepository.findAll();
        assertThat(branchOfficeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = branchOfficeRepository.findAll().size();
        // set the field null
        branchOffice.setName(null);

        // Create the BranchOffice, which fails.
        BranchOfficeDTO branchOfficeDTO = branchOfficeMapper.toDto(branchOffice);

        restBranchOfficeMockMvc.perform(post("/api/branch-offices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(branchOfficeDTO)))
            .andExpect(status().isBadRequest());

        List<BranchOffice> branchOfficeList = branchOfficeRepository.findAll();
        assertThat(branchOfficeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = branchOfficeRepository.findAll().size();
        // set the field null
        branchOffice.setStatus(null);

        // Create the BranchOffice, which fails.
        BranchOfficeDTO branchOfficeDTO = branchOfficeMapper.toDto(branchOffice);

        restBranchOfficeMockMvc.perform(post("/api/branch-offices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(branchOfficeDTO)))
            .andExpect(status().isBadRequest());

        List<BranchOffice> branchOfficeList = branchOfficeRepository.findAll();
        assertThat(branchOfficeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBranchOffices() throws Exception {
        // Initialize the database
        branchOfficeRepository.saveAndFlush(branchOffice);

        // Get all the branchOfficeList
        restBranchOfficeMockMvc.perform(get("/api/branch-offices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(branchOffice.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getBranchOffice() throws Exception {
        // Initialize the database
        branchOfficeRepository.saveAndFlush(branchOffice);

        // Get the branchOffice
        restBranchOfficeMockMvc.perform(get("/api/branch-offices/{id}", branchOffice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(branchOffice.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getAllBranchOfficesByNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        branchOfficeRepository.saveAndFlush(branchOffice);

        // Get all the branchOfficeList where number equals to DEFAULT_NUMBER
        defaultBranchOfficeShouldBeFound("number.equals=" + DEFAULT_NUMBER);

        // Get all the branchOfficeList where number equals to UPDATED_NUMBER
        defaultBranchOfficeShouldNotBeFound("number.equals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllBranchOfficesByNumberIsInShouldWork() throws Exception {
        // Initialize the database
        branchOfficeRepository.saveAndFlush(branchOffice);

        // Get all the branchOfficeList where number in DEFAULT_NUMBER or UPDATED_NUMBER
        defaultBranchOfficeShouldBeFound("number.in=" + DEFAULT_NUMBER + "," + UPDATED_NUMBER);

        // Get all the branchOfficeList where number equals to UPDATED_NUMBER
        defaultBranchOfficeShouldNotBeFound("number.in=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllBranchOfficesByNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        branchOfficeRepository.saveAndFlush(branchOffice);

        // Get all the branchOfficeList where number is not null
        defaultBranchOfficeShouldBeFound("number.specified=true");

        // Get all the branchOfficeList where number is null
        defaultBranchOfficeShouldNotBeFound("number.specified=false");
    }

    @Test
    @Transactional
    public void getAllBranchOfficesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        branchOfficeRepository.saveAndFlush(branchOffice);

        // Get all the branchOfficeList where name equals to DEFAULT_NAME
        defaultBranchOfficeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the branchOfficeList where name equals to UPDATED_NAME
        defaultBranchOfficeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllBranchOfficesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        branchOfficeRepository.saveAndFlush(branchOffice);

        // Get all the branchOfficeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultBranchOfficeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the branchOfficeList where name equals to UPDATED_NAME
        defaultBranchOfficeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllBranchOfficesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        branchOfficeRepository.saveAndFlush(branchOffice);

        // Get all the branchOfficeList where name is not null
        defaultBranchOfficeShouldBeFound("name.specified=true");

        // Get all the branchOfficeList where name is null
        defaultBranchOfficeShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllBranchOfficesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        branchOfficeRepository.saveAndFlush(branchOffice);

        // Get all the branchOfficeList where status equals to DEFAULT_STATUS
        defaultBranchOfficeShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the branchOfficeList where status equals to UPDATED_STATUS
        defaultBranchOfficeShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllBranchOfficesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        branchOfficeRepository.saveAndFlush(branchOffice);

        // Get all the branchOfficeList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultBranchOfficeShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the branchOfficeList where status equals to UPDATED_STATUS
        defaultBranchOfficeShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllBranchOfficesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        branchOfficeRepository.saveAndFlush(branchOffice);

        // Get all the branchOfficeList where status is not null
        defaultBranchOfficeShouldBeFound("status.specified=true");

        // Get all the branchOfficeList where status is null
        defaultBranchOfficeShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllBranchOfficesByAffiliateIsEqualToSomething() throws Exception {
        // Initialize the database
        branchOfficeRepository.saveAndFlush(branchOffice);
        Affiliate affiliate = AffiliateResourceIT.createEntity(em);
        em.persist(affiliate);
        em.flush();
        branchOffice.setAffiliate(affiliate);
        branchOfficeRepository.saveAndFlush(branchOffice);
        Long affiliateId = affiliate.getId();

        // Get all the branchOfficeList where affiliate equals to affiliateId
        defaultBranchOfficeShouldBeFound("affiliateId.equals=" + affiliateId);

        // Get all the branchOfficeList where affiliate equals to affiliateId + 1
        defaultBranchOfficeShouldNotBeFound("affiliateId.equals=" + (affiliateId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     *
    private void defaultBranchOfficeShouldBeFound(String filter) throws Exception {
        restBranchOfficeMockMvc.perform(get("/api/branch-offices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(branchOffice.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));

        // Check, that the count call also returns 1
        restBranchOfficeMockMvc.perform(get("/api/branch-offices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     *
    private void defaultBranchOfficeShouldNotBeFound(String filter) throws Exception {
        restBranchOfficeMockMvc.perform(get("/api/branch-offices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBranchOfficeMockMvc.perform(get("/api/branch-offices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingBranchOffice() throws Exception {
        // Get the branchOffice
        restBranchOfficeMockMvc.perform(get("/api/branch-offices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBranchOffice() throws Exception {
        // Initialize the database
        branchOfficeRepository.saveAndFlush(branchOffice);

        int databaseSizeBeforeUpdate = branchOfficeRepository.findAll().size();

        // Update the branchOffice
        BranchOffice updatedBranchOffice = branchOfficeRepository.findById(branchOffice.getId()).get();
        // Disconnect from session so that the updates on updatedBranchOffice are not directly saved in db
        em.detach(updatedBranchOffice);
        updatedBranchOffice
            .number(UPDATED_NUMBER)
            .name(UPDATED_NAME)
            .status(UPDATED_STATUS);
        BranchOfficeDTO branchOfficeDTO = branchOfficeMapper.toDto(updatedBranchOffice);

        restBranchOfficeMockMvc.perform(put("/api/branch-offices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(branchOfficeDTO)))
            .andExpect(status().isOk());

        // Validate the BranchOffice in the database
        List<BranchOffice> branchOfficeList = branchOfficeRepository.findAll();
        assertThat(branchOfficeList).hasSize(databaseSizeBeforeUpdate);
        BranchOffice testBranchOffice = branchOfficeList.get(branchOfficeList.size() - 1);
        assertThat(testBranchOffice.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testBranchOffice.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBranchOffice.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingBranchOffice() throws Exception {
        int databaseSizeBeforeUpdate = branchOfficeRepository.findAll().size();

        // Create the BranchOffice
        BranchOfficeDTO branchOfficeDTO = branchOfficeMapper.toDto(branchOffice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBranchOfficeMockMvc.perform(put("/api/branch-offices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(branchOfficeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BranchOffice in the database
        List<BranchOffice> branchOfficeList = branchOfficeRepository.findAll();
        assertThat(branchOfficeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBranchOffice() throws Exception {
        // Initialize the database
        branchOfficeRepository.saveAndFlush(branchOffice);

        int databaseSizeBeforeDelete = branchOfficeRepository.findAll().size();

        // Delete the branchOffice
        restBranchOfficeMockMvc.perform(delete("/api/branch-offices/{id}", branchOffice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BranchOffice> branchOfficeList = branchOfficeRepository.findAll();
        assertThat(branchOfficeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BranchOffice.class);
        BranchOffice branchOffice1 = new BranchOffice();
        branchOffice1.setId(1L);
        BranchOffice branchOffice2 = new BranchOffice();
        branchOffice2.setId(branchOffice1.getId());
        assertThat(branchOffice1).isEqualTo(branchOffice2);
        branchOffice2.setId(2L);
        assertThat(branchOffice1).isNotEqualTo(branchOffice2);
        branchOffice1.setId(null);
        assertThat(branchOffice1).isNotEqualTo(branchOffice2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BranchOfficeDTO.class);
        BranchOfficeDTO branchOfficeDTO1 = new BranchOfficeDTO();
        branchOfficeDTO1.setId(1L);
        BranchOfficeDTO branchOfficeDTO2 = new BranchOfficeDTO();
        assertThat(branchOfficeDTO1).isNotEqualTo(branchOfficeDTO2);
        branchOfficeDTO2.setId(branchOfficeDTO1.getId());
        assertThat(branchOfficeDTO1).isEqualTo(branchOfficeDTO2);
        branchOfficeDTO2.setId(2L);
        assertThat(branchOfficeDTO1).isNotEqualTo(branchOfficeDTO2);
        branchOfficeDTO1.setId(null);
        assertThat(branchOfficeDTO1).isNotEqualTo(branchOfficeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(branchOfficeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(branchOfficeMapper.fromId(null)).isNull();
    }
    */
}
