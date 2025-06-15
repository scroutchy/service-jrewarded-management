package com.scr.project.sjrm.domains.rewarded.repository;

import com.scr.project.sjrm.RewardedTestDataUtil;
import com.scr.project.sjrm.domains.rewarded.model.entity.Reward;
import com.scr.project.sjrm.domains.rewarded.model.entity.Rewarded;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;

import java.util.List;

import static com.scr.project.sjrm.domains.rewarded.model.entity.RewardedType.ACTOR;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RewardedRepositoryTest {

    @Autowired
    private RewardedRepository rewardedRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        RewardedTestDataUtil.initTestData(entityManager, rewardedRepository);
    }


    @Test
    void saveShouldSucceed() {
        var rewarded = new Rewarded().setRewardedId("rewardedId").setType(ACTOR);
        var saved = rewardedRepository.save(rewarded);
        assertThat(saved).isNotNull();
        var found = rewardedRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getId()).isNotNull();
        assertThat(found.get().getRewardedId()).isEqualTo("rewardedId");
        assertThat(found.get().getType()).isEqualTo(ACTOR);
        assertThat(found.get().getRewards()).isEmpty();
    }

    @Test
    void saveRewardedWithRewardsShouldSucceed() {
        var reward1 = new Reward().setType("Oscar").setYear(2020).setCategory("Best Actor");
        var reward2 = new Reward().setType("Golden Globe").setYear(2021).setCategory("Best Actor");
        var rewarded = new Rewarded().setRewardedId("rewardedId").setType(ACTOR).setRewards(List.of(reward1, reward2));

        var saved = rewardedRepository.save(rewarded);

        var found = rewardedRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getRewards()).hasSize(2);
        assertThat(found.get().getRewards().get(0).getId()).isNotNull();
        assertThat(found.get().getRewards().get(0).getType()).isEqualTo("Oscar");
        assertThat(found.get().getRewards().get(0).getYear()).isEqualTo(2020);
        assertThat(found.get().getRewards().get(0).getCategory()).isEqualTo("Best Actor");
        assertThat(found.get().getRewards().get(1).getId()).isNotNull();
        assertThat(found.get().getRewards().get(1).getType()).isEqualTo("Golden Globe");
        assertThat(found.get().getRewards().get(1).getYear()).isEqualTo(2021);
        assertThat(found.get().getRewards().get(1).getCategory()).isEqualTo("Best Actor");
    }

    @Test
    void findByIdShouldReturnRewardedWhenExists() {
        var id = rewardedRepository.findOne(Example.of(new Rewarded().setRewardedId("rewardedId1"))).get().getId();
        var maybeFound = rewardedRepository.findById(id);
        assertThat(maybeFound).isPresent();
        var found = maybeFound.get();
        assertThat(found.getId()).isNotNull();
        assertThat(found.getRewardedId()).isEqualTo("rewardedId1");
        assertThat(found.getType()).isEqualTo(ACTOR);
        assertThat(found.getRewards()).isEmpty();
    }

    @Test
    void findByIdShouldReturnRewardedWithRewardsWhenExists() {
        var id = rewardedRepository.findOne(Example.of(new Rewarded().setRewardedId("rewardedId2"))).get().getId();
        var maybeFound = rewardedRepository.findById(id);
        assertThat(maybeFound).isPresent();
        var found = maybeFound.get();
        assertThat(found.getId()).isNotNull();
        assertThat(found.getRewardedId()).isEqualTo("rewardedId2");
        assertThat(found.getType()).isEqualTo(ACTOR);
        assertThat(found.getRewards()).hasSize(2);
        assertThat(found.getRewards().get(0).getType()).isEqualTo("Oscar");
        assertThat(found.getRewards().get(1).getType()).isEqualTo("Golden Globe");
    }

    @Test
    void findByIdShouldReturnEmptyWhenNotExists() {
        var maybeFound = rewardedRepository.findById(999L);
        assertThat(maybeFound).isNotPresent();
    }
}
