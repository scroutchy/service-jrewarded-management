package com.scr.project.sjrm.entrypoint.integration.resource;

import com.scr.project.sjrm.AbstractIntegrationTest;
import com.scr.project.sjrm.domains.rewarded.model.entity.Rewarded;
import com.scr.project.sjrm.domains.rewarded.repository.RewardedRepository;
import com.scr.project.sjrm.domains.rewarded.service.RewardedService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static com.scr.project.sjrm.RewardedTestDataUtil.initTestData;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RewardedResourceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RewardedService rewardedService;

    @Autowired
    private RewardedRepository rewardedRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        initTestData(entityManager, rewardedRepository);
    }

    @Test
    void findShouldSucceedWhenExists() throws Exception {
        var id = rewardedRepository.findOne(Example.of(new Rewarded().setRewardedId("rewardedId1"))).get().getId();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/rewarded/" + id))
               .andExpect(status().isOk())
               .andExpect(content().contentType(APPLICATION_JSON_VALUE))
               .andExpect(jsonPath("$.id").isNotEmpty())
               .andExpect(jsonPath("$.rewardedId", is("rewardedId1")))
               .andExpect(jsonPath("$.type", is("ACTOR")))
               .andExpect(jsonPath("$.rewards").isEmpty());
    }

    @Test
    void findShouldReturnNotFoundWhenDoesNotExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/rewarded/" + 999L))
               .andExpect(status().isNotFound());
    }

}
