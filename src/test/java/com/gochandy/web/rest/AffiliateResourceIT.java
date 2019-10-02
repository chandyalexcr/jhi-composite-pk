package com.gochandy.web.rest;

import com.gochandy.TestJhiApp;
import com.gochandy.domain.Affiliate;
import com.gochandy.repository.AffiliateRepository;
import com.gochandy.service.AffiliateService;
import com.gochandy.service.dto.AffiliateDTO;
import com.gochandy.service.mapper.AffiliateMapper;
import com.gochandy.web.rest.errors.ExceptionTranslator;
import com.gochandy.service.dto.AffiliateCriteria;
import com.gochandy.service.AffiliateQueryService;

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
 * Integration tests for the {@link AffiliateResource} REST controller.
 */
@SpringBootTest(classes = TestJhiApp.class)
public class AffiliateResourceIT {

    /*private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private AffiliateRepository affiliateRepository;

    @Autowired
    private AffiliateMapper affiliateMapper;

    @Autowired
    private AffiliateService affiliateService;

    @Autowired
    private AffiliateQueryService affiliateQueryService;

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

    private MockMvc restAffiliateMockMvc;

    private Affiliate affiliate;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AffiliateResource affiliateResource = new AffiliateResource(affiliateService, affiliateQueryService);
        this.restAffiliateMockMvc = MockMvcBuilders.standaloneSetup(affiliateResource)
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
     *
    public static Affiliate createEntity(EntityManager em) {
        Affiliate affiliate = new Affiliate()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME);
        return affiliate;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     *
    public static Affiliate createUpdatedEntity(EntityManager em) {
        Affiliate affiliate = new Affiliate()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME);
        return affiliate;
    }

    @BeforeEach
    public void initTest() {
        affiliate = createEntity(em);
    }

    @Test
    @Transactional
    public void createAffiliate() throws Exception {
        int databaseSizeBeforeCreate = affiliateRepository.findAll().size();

        // Create the Affiliate
        AffiliateDTO affiliateDTO = affiliateMapper.toDto(affiliate);
        restAffiliateMockMvc.perform(post("/api/affiliates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(affiliateDTO)))
            .andExpect(status().isCreated());

        // Validate the Affiliate in the database
        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeCreate + 1);
        Affiliate testAffiliate = affiliateList.get(affiliateList.size() - 1);
        assertThat(testAffiliate.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAffiliate.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createAffiliateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = affiliateRepository.findAll().size();

        // Create the Affiliate with an existing ID
        affiliate.setId(1L);
        AffiliateDTO affiliateDTO = affiliateMapper.toDto(affiliate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAffiliateMockMvc.perform(post("/api/affiliates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(affiliateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Affiliate in the database
        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = affiliateRepository.findAll().size();
        // set the field null
        affiliate.setCode(null);

        // Create the Affiliate, which fails.
        AffiliateDTO affiliateDTO = affiliateMapper.toDto(affiliate);

        restAffiliateMockMvc.perform(post("/api/affiliates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(affiliateDTO)))
            .andExpect(status().isBadRequest());

        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = affiliateRepository.findAll().size();
        // set the field null
        affiliate.setName(null);

        // Create the Affiliate, which fails.
        AffiliateDTO affiliateDTO = affiliateMapper.toDto(affiliate);

        restAffiliateMockMvc.perform(post("/api/affiliates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(affiliateDTO)))
            .andExpect(status().isBadRequest());

        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAffiliates() throws Exception {
        // Initialize the database
        affiliateRepository.saveAndFlush(affiliate);

        // Get all the affiliateList
        restAffiliateMockMvc.perform(get("/api/affiliates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(affiliate.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getAffiliate() throws Exception {
        // Initialize the database
        affiliateRepository.saveAndFlush(affiliate);

        // Get the affiliate
        restAffiliateMockMvc.perform(get("/api/affiliates/{id}", affiliate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(affiliate.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getAllAffiliatesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        affiliateRepository.saveAndFlush(affiliate);

        // Get all the affiliateList where code equals to DEFAULT_CODE
        defaultAffiliateShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the affiliateList where code equals to UPDATED_CODE
        defaultAffiliateShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllAffiliatesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        affiliateRepository.saveAndFlush(affiliate);

        // Get all the affiliateList where code in DEFAULT_CODE or UPDATED_CODE
        defaultAffiliateShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the affiliateList where code equals to UPDATED_CODE
        defaultAffiliateShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllAffiliatesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        affiliateRepository.saveAndFlush(affiliate);

        // Get all the affiliateList where code is not null
        defaultAffiliateShouldBeFound("code.specified=true");

        // Get all the affiliateList where code is null
        defaultAffiliateShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllAffiliatesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        affiliateRepository.saveAndFlush(affiliate);

        // Get all the affiliateList where name equals to DEFAULT_NAME
        defaultAffiliateShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the affiliateList where name equals to UPDATED_NAME
        defaultAffiliateShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAffiliatesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        affiliateRepository.saveAndFlush(affiliate);

        // Get all the affiliateList where name in DEFAULT_NAME or UPDATED_NAME
        defaultAffiliateShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the affiliateList where name equals to UPDATED_NAME
        defaultAffiliateShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAffiliatesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        affiliateRepository.saveAndFlush(affiliate);

        // Get all the affiliateList where name is not null
        defaultAffiliateShouldBeFound("name.specified=true");

        // Get all the affiliateList where name is null
        defaultAffiliateShouldNotBeFound("name.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     *
    private void defaultAffiliateShouldBeFound(String filter) throws Exception {
        restAffiliateMockMvc.perform(get("/api/affiliates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(affiliate.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restAffiliateMockMvc.perform(get("/api/affiliates/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     *
    private void defaultAffiliateShouldNotBeFound(String filter) throws Exception {
        restAffiliateMockMvc.perform(get("/api/affiliates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAffiliateMockMvc.perform(get("/api/affiliates/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAffiliate() throws Exception {
        // Get the affiliate
        restAffiliateMockMvc.perform(get("/api/affiliates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAffiliate() throws Exception {
        // Initialize the database
        affiliateRepository.saveAndFlush(affiliate);

        int databaseSizeBeforeUpdate = affiliateRepository.findAll().size();

        // Update the affiliate
        Affiliate updatedAffiliate = affiliateRepository.findById(affiliate.getId()).get();
        // Disconnect from session so that the updates on updatedAffiliate are not directly saved in db
        em.detach(updatedAffiliate);
        updatedAffiliate
            .code(UPDATED_CODE)
            .name(UPDATED_NAME);
        AffiliateDTO affiliateDTO = affiliateMapper.toDto(updatedAffiliate);

        restAffiliateMockMvc.perform(put("/api/affiliates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(affiliateDTO)))
            .andExpect(status().isOk());

        // Validate the Affiliate in the database
        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeUpdate);
        Affiliate testAffiliate = affiliateList.get(affiliateList.size() - 1);
        assertThat(testAffiliate.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAffiliate.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingAffiliate() throws Exception {
        int databaseSizeBeforeUpdate = affiliateRepository.findAll().size();

        // Create the Affiliate
        AffiliateDTO affiliateDTO = affiliateMapper.toDto(affiliate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAffiliateMockMvc.perform(put("/api/affiliates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(affiliateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Affiliate in the database
        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAffiliate() throws Exception {
        // Initialize the database
        affiliateRepository.saveAndFlush(affiliate);

        int databaseSizeBeforeDelete = affiliateRepository.findAll().size();

        // Delete the affiliate
        restAffiliateMockMvc.perform(delete("/api/affiliates/{id}", affiliate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Affiliate.class);
        Affiliate affiliate1 = new Affiliate();
        affiliate1.setId(1L);
        Affiliate affiliate2 = new Affiliate();
        affiliate2.setId(affiliate1.getId());
        assertThat(affiliate1).isEqualTo(affiliate2);
        affiliate2.setId(2L);
        assertThat(affiliate1).isNotEqualTo(affiliate2);
        affiliate1.setId(null);
        assertThat(affiliate1).isNotEqualTo(affiliate2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AffiliateDTO.class);
        AffiliateDTO affiliateDTO1 = new AffiliateDTO();
        affiliateDTO1.setId(1L);
        AffiliateDTO affiliateDTO2 = new AffiliateDTO();
        assertThat(affiliateDTO1).isNotEqualTo(affiliateDTO2);
        affiliateDTO2.setId(affiliateDTO1.getId());
        assertThat(affiliateDTO1).isEqualTo(affiliateDTO2);
        affiliateDTO2.setId(2L);
        assertThat(affiliateDTO1).isNotEqualTo(affiliateDTO2);
        affiliateDTO1.setId(null);
        assertThat(affiliateDTO1).isNotEqualTo(affiliateDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(affiliateMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(affiliateMapper.fromId(null)).isNull();
    }
*/
}
