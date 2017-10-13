package br.com.trrosa.riskassessment.web.rest;

import br.com.trrosa.riskassessment.RiskassessmentApplication;
import br.com.trrosa.riskassessment.domain.RiskAssessment;
import br.com.trrosa.riskassessment.domain.enumeration.RiskType;
import br.com.trrosa.riskassessment.repository.RiskAssessmentRepository;
import br.com.trrosa.riskassessment.service.RiskAssessmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author tiago
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RiskassessmentApplication.class)
public class RiskAssessmentResourceTest {

    @Autowired
    private RiskAssessmentRepository riskAssessmentRepository;

    @Autowired
    private RiskAssessmentService riskAssessmentService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RiskAssessmentResource riskAssessmentResource = new RiskAssessmentResource(riskAssessmentService);
        this.restMockMvc = MockMvcBuilders.standaloneSetup(riskAssessmentResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        riskAssessmentRepository.deleteAll();
    }

    private static final String CUSTOMER_NAME = "AAAA";
    private static final BigDecimal CREDIT_LIMIT = BigDecimal.ONE;
    private static final RiskType RISK = RiskType.B;

    private static RiskAssessment createEntity() {
        return RiskAssessment.builder()
                .customerName(CUSTOMER_NAME)
                .creditLimit(CREDIT_LIMIT)
                .risk(RISK).build();
    }

    @Test
    @Transactional
    public void createRisk() throws Exception {
        int databaseSizeBeforeCreate = Long.valueOf(riskAssessmentRepository.count()).intValue();

        restMockMvc.perform(post("/api/risk-assessment")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(createEntity())))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.customerName").value(CUSTOMER_NAME))
                .andExpect(jsonPath("$.creditLimit").value(CREDIT_LIMIT.toPlainString()))
                .andExpect(jsonPath("$.risk").value(RISK.name()))
                .andExpect(jsonPath("$.rate").value(RISK.getRate().toPlainString()));

        List<RiskAssessment> riskAssessments = riskAssessmentRepository.findAll();
        assertThat(riskAssessments).hasSize(databaseSizeBeforeCreate + 1);
        RiskAssessment testRiskAssessment = riskAssessments.stream().findFirst().orElse(null);
        Assert.assertNotNull(testRiskAssessment);
        assertThat(testRiskAssessment.getCustomerName()).isEqualTo(CUSTOMER_NAME);
        assertThat(testRiskAssessment.getCreditLimit()).isEqualTo(CREDIT_LIMIT);
        assertThat(testRiskAssessment.getRisk()).isEqualTo(RISK);
        assertThat(testRiskAssessment.getRate()).isEqualTo(RISK.getRate());

    }

    @Test
    @Transactional
    public void createRiskWithId() throws Exception {

        RiskAssessment riskAssessment = createEntity();
        riskAssessment.setId(1l);

        restMockMvc.perform(post("/api/risk-assessment")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(riskAssessment)))
                .andExpect(status().isBadRequest());

    }

    @Test
    @Transactional
    public void checkIfCustomerNameIsRequired() throws Exception {

        RiskAssessment riskAssessment = createEntity();
        riskAssessment.setCustomerName(null);

        restMockMvc.perform(post("/api/risk-assessment")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(riskAssessment)))
                .andExpect(status().isBadRequest());

    }

    @Test
    @Transactional
    public void checkIfCreditLimitIsRequired() throws Exception {

        RiskAssessment riskAssessment = createEntity();
        riskAssessment.setCreditLimit(null);

        restMockMvc.perform(post("/api/risk-assessment")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(riskAssessment)))
                .andExpect(status().isBadRequest());

    }

    @Test
    @Transactional
    public void checkIfRiskIsRequired() throws Exception {

        RiskAssessment riskAssessment = createEntity();
        riskAssessment.setRisk(null);

        restMockMvc.perform(post("/api/risk-assessment")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(riskAssessment)))
                .andExpect(status().isBadRequest());

    }

    @Test
    @Transactional
    public void getAllRisk() throws Exception {
        riskAssessmentRepository.saveAndFlush(createEntity());
        restMockMvc.perform(get("/api/risk-assessment?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.content.[*].customerName").value(hasItem(CUSTOMER_NAME)))
                .andExpect(jsonPath("$.content.[0].creditLimit").value(CREDIT_LIMIT.toPlainString()))
                .andExpect(jsonPath("$.content.[*].risk").value(hasItem(RISK.name())));
    }

    @Test
    @Transactional
    public void updateRisk() throws Exception {
        RiskAssessment r = riskAssessmentRepository.saveAndFlush(createEntity());
        r.setRisk(RiskType.C);
        restMockMvc.perform(put("/api/risk-assessment").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(r)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.customerName").value(CUSTOMER_NAME))
                .andExpect(jsonPath("$.creditLimit").value(CREDIT_LIMIT.toPlainString()))
                .andExpect(jsonPath("$.risk").value(RiskType.C.name()))
                .andExpect(jsonPath("$.rate").value(RiskType.C.getRate().toPlainString()));
    }

    @Test
    @Transactional
    public void deleteRisk() throws Exception {
        RiskAssessment r = riskAssessmentRepository.saveAndFlush(createEntity());
        int databaseSizeBeforeDelete = Long.valueOf(riskAssessmentRepository.count()).intValue();

        restMockMvc.perform(delete("/api/risk-assessment/{id}", r.getId())
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk());

        boolean riskExists = riskAssessmentRepository.exists(r.getId());
        assertThat(riskExists).isFalse();

        // Validate the database is empty
        List<RiskAssessment> configRendimentoList = riskAssessmentRepository.findAll();
        assertThat(configRendimentoList).hasSize(databaseSizeBeforeDelete - 1);
    }

}
