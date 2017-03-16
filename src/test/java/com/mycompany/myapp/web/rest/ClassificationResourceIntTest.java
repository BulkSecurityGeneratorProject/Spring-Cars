package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SpringCarsApp;

import com.mycompany.myapp.domain.Classification;
import com.mycompany.myapp.repository.ClassificationRepository;

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
 * Test class for the ClassificationResource REST controller.
 *
 * @see ClassificationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringCarsApp.class)
public class ClassificationResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private ClassificationRepository classificationRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restClassificationMockMvc;

    private Classification classification;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClassificationResource classificationResource = new ClassificationResource();
        ReflectionTestUtils.setField(classificationResource, "classificationRepository", classificationRepository);
        this.restClassificationMockMvc = MockMvcBuilders.standaloneSetup(classificationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Classification createEntity(EntityManager em) {
        Classification classification = new Classification()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION);
        return classification;
    }

    @Before
    public void initTest() {
        classification = createEntity(em);
    }

    @Test
    @Transactional
    public void createClassification() throws Exception {
        int databaseSizeBeforeCreate = classificationRepository.findAll().size();

        // Create the Classification

        restClassificationMockMvc.perform(post("/api/classifications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(classification)))
                .andExpect(status().isCreated());

        // Validate the Classification in the database
        List<Classification> classifications = classificationRepository.findAll();
        assertThat(classifications).hasSize(databaseSizeBeforeCreate + 1);
        Classification testClassification = classifications.get(classifications.size() - 1);
        assertThat(testClassification.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testClassification.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllClassifications() throws Exception {
        // Initialize the database
        classificationRepository.saveAndFlush(classification);

        // Get all the classifications
        restClassificationMockMvc.perform(get("/api/classifications?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(classification.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getClassification() throws Exception {
        // Initialize the database
        classificationRepository.saveAndFlush(classification);

        // Get the classification
        restClassificationMockMvc.perform(get("/api/classifications/{id}", classification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(classification.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClassification() throws Exception {
        // Get the classification
        restClassificationMockMvc.perform(get("/api/classifications/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClassification() throws Exception {
        // Initialize the database
        classificationRepository.saveAndFlush(classification);
        int databaseSizeBeforeUpdate = classificationRepository.findAll().size();

        // Update the classification
        Classification updatedClassification = classificationRepository.findOne(classification.getId());
        updatedClassification
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION);

        restClassificationMockMvc.perform(put("/api/classifications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedClassification)))
                .andExpect(status().isOk());

        // Validate the Classification in the database
        List<Classification> classifications = classificationRepository.findAll();
        assertThat(classifications).hasSize(databaseSizeBeforeUpdate);
        Classification testClassification = classifications.get(classifications.size() - 1);
        assertThat(testClassification.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClassification.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteClassification() throws Exception {
        // Initialize the database
        classificationRepository.saveAndFlush(classification);
        int databaseSizeBeforeDelete = classificationRepository.findAll().size();

        // Get the classification
        restClassificationMockMvc.perform(delete("/api/classifications/{id}", classification.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Classification> classifications = classificationRepository.findAll();
        assertThat(classifications).hasSize(databaseSizeBeforeDelete - 1);
    }
}
