package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SpringCarsApp;

import com.mycompany.myapp.domain.Traction;
import com.mycompany.myapp.repository.TractionRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TractionResource REST controller.
 *
 * @see TractionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringCarsApp.class)
public class TractionResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final String DEFAULT_REF = "AAAAA";
    private static final String UPDATED_REF = "BBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private TractionRepository tractionRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTractionMockMvc;

    private Traction traction;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TractionResource tractionResource = new TractionResource();
        ReflectionTestUtils.setField(tractionResource, "tractionRepository", tractionRepository);
        this.restTractionMockMvc = MockMvcBuilders.standaloneSetup(tractionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Traction createEntity(EntityManager em) {
        Traction traction = new Traction()
                .name(DEFAULT_NAME)
                .ref(DEFAULT_REF)
                .description(DEFAULT_DESCRIPTION);
        return traction;
    }

    @Before
    public void initTest() {
        traction = createEntity(em);
    }

    @Test
    @Transactional
    public void createTraction() throws Exception {
        int databaseSizeBeforeCreate = tractionRepository.findAll().size();

        // Create the Traction

        restTractionMockMvc.perform(post("/api/tractions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traction)))
                .andExpect(status().isCreated());

        // Validate the Traction in the database
        List<Traction> tractions = tractionRepository.findAll();
        assertThat(tractions).hasSize(databaseSizeBeforeCreate + 1);
        Traction testTraction = tractions.get(tractions.size() - 1);
        assertThat(testTraction.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTraction.getRef()).isEqualTo(DEFAULT_REF);
        assertThat(testTraction.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTractions() throws Exception {
        // Initialize the database
        tractionRepository.saveAndFlush(traction);

        // Get all the tractions
        restTractionMockMvc.perform(get("/api/tractions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(traction.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].ref").value(hasItem(DEFAULT_REF.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getTraction() throws Exception {
        // Initialize the database
        tractionRepository.saveAndFlush(traction);

        // Get the traction
        restTractionMockMvc.perform(get("/api/tractions/{id}", traction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(traction.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.ref").value(DEFAULT_REF.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTraction() throws Exception {
        // Get the traction
        restTractionMockMvc.perform(get("/api/tractions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTraction() throws Exception {
        // Initialize the database
        tractionRepository.saveAndFlush(traction);
        int databaseSizeBeforeUpdate = tractionRepository.findAll().size();

        // Update the traction
        Traction updatedTraction = tractionRepository.findOne(traction.getId());
        updatedTraction
                .name(UPDATED_NAME)
                .ref(UPDATED_REF)
                .description(UPDATED_DESCRIPTION);

        restTractionMockMvc.perform(put("/api/tractions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedTraction)))
                .andExpect(status().isOk());

        // Validate the Traction in the database
        List<Traction> tractions = tractionRepository.findAll();
        assertThat(tractions).hasSize(databaseSizeBeforeUpdate);
        Traction testTraction = tractions.get(tractions.size() - 1);
        assertThat(testTraction.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTraction.getRef()).isEqualTo(UPDATED_REF);
        assertThat(testTraction.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteTraction() throws Exception {
        // Initialize the database
        tractionRepository.saveAndFlush(traction);
        int databaseSizeBeforeDelete = tractionRepository.findAll().size();

        // Get the traction
        restTractionMockMvc.perform(delete("/api/tractions/{id}", traction.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Traction> tractions = tractionRepository.findAll();
        assertThat(tractions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
