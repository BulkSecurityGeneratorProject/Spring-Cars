package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SpringCarsApp;

import com.mycompany.myapp.domain.Gas;
import com.mycompany.myapp.repository.GasRepository;

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
 * Test class for the GasResource REST controller.
 *
 * @see GasResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringCarsApp.class)
public class GasResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private GasRepository gasRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restGasMockMvc;

    private Gas gas;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GasResource gasResource = new GasResource();
        ReflectionTestUtils.setField(gasResource, "gasRepository", gasRepository);
        this.restGasMockMvc = MockMvcBuilders.standaloneSetup(gasResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gas createEntity(EntityManager em) {
        Gas gas = new Gas()
                .name(DEFAULT_NAME);
        return gas;
    }

    @Before
    public void initTest() {
        gas = createEntity(em);
    }

    @Test
    @Transactional
    public void createGas() throws Exception {
        int databaseSizeBeforeCreate = gasRepository.findAll().size();

        // Create the Gas

        restGasMockMvc.perform(post("/api/gases")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(gas)))
                .andExpect(status().isCreated());

        // Validate the Gas in the database
        List<Gas> gases = gasRepository.findAll();
        assertThat(gases).hasSize(databaseSizeBeforeCreate + 1);
        Gas testGas = gases.get(gases.size() - 1);
        assertThat(testGas.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void getAllGases() throws Exception {
        // Initialize the database
        gasRepository.saveAndFlush(gas);

        // Get all the gases
        restGasMockMvc.perform(get("/api/gases?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(gas.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getGas() throws Exception {
        // Initialize the database
        gasRepository.saveAndFlush(gas);

        // Get the gas
        restGasMockMvc.perform(get("/api/gases/{id}", gas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gas.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGas() throws Exception {
        // Get the gas
        restGasMockMvc.perform(get("/api/gases/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGas() throws Exception {
        // Initialize the database
        gasRepository.saveAndFlush(gas);
        int databaseSizeBeforeUpdate = gasRepository.findAll().size();

        // Update the gas
        Gas updatedGas = gasRepository.findOne(gas.getId());
        updatedGas
                .name(UPDATED_NAME);

        restGasMockMvc.perform(put("/api/gases")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedGas)))
                .andExpect(status().isOk());

        // Validate the Gas in the database
        List<Gas> gases = gasRepository.findAll();
        assertThat(gases).hasSize(databaseSizeBeforeUpdate);
        Gas testGas = gases.get(gases.size() - 1);
        assertThat(testGas.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteGas() throws Exception {
        // Initialize the database
        gasRepository.saveAndFlush(gas);
        int databaseSizeBeforeDelete = gasRepository.findAll().size();

        // Get the gas
        restGasMockMvc.perform(delete("/api/gases/{id}", gas.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Gas> gases = gasRepository.findAll();
        assertThat(gases).hasSize(databaseSizeBeforeDelete - 1);
    }
}
