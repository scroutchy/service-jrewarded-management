package com.scr.project.sjrm.entrypoint.integration.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scr.project.sjrm.AbstractIntegrationTest;
import com.scr.project.sjrm.domains.rewarded.model.entity.Rewarded;
import com.scr.project.sjrm.domains.rewarded.repository.RewardedRepository;
import com.scr.project.sjrm.domains.rewarded.service.RewardedService;
import com.scr.project.sjrm.entrypoint.model.api.RewardApiDto;
import com.scr.project.sjrm.entrypoint.model.api.RewardedApiDto;
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
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
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
    private RewardedService rewardedService;

    @Autowired
    private RewardedRepository rewardedRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

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

    @Test
    void addRewardShouldSucceedWhenExists() throws Exception {
        var id = rewardedRepository.findOne(Example.of(new Rewarded().setRewardedId("rewardedId1"))).get().getId();
        var rewardDto = new RewardApiDto().setCategory("Best Actor").setType("Oscar").setYear(2022);
        var result = mockMvc.perform(MockMvcRequestBuilders.post("/api/rewarded/" + id + "/rewards")
                                                           .contentType(APPLICATION_JSON)
                                                           .content(objectMapper.writeValueAsString(rewardDto)))
                            .andExpect(status().isOk())
//               .andExpect(jsonPath("$.id").isNotEmpty())
//               .andExpect(jsonPath("$.rewardedId", is("rewardedId1")))
//               .andExpect(jsonPath("$.type", is("ACTOR")))
//               .andExpect(jsonPath("$.rewards").isNotEmpty())
//               .andExpect(jsonPath("$.rewards", hasSize(1)));
                            .andReturn();
        var response = objectMapper.readValue(result.getResponse().getContentAsString(), RewardedApiDto.class);
        assertThat(response.getId()).isNotNull();
        assertThat(response.getRewardedId()).isEqualTo("rewardedId1");
        assertThat(response.getType()).hasToString("ACTOR");
        assertThat(response.getRewards()).hasSize(1);
        assertThat(response.getRewards().get(0).getCategory()).isEqualTo("Best Actor");
        assertThat(response.getRewards().get(0).getType()).isEqualTo("Oscar");
        assertThat(response.getRewards().get(0).getYear()).isEqualTo(2022);

    }

}
