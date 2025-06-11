package com.scr.project.sjrm.domains.rewarded.repository;

import com.scr.project.sjrm.domains.rewarded.model.entity.Rewarded;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.scr.project.sjrm.domains.rewarded.model.entity.RewardedType.ACTOR;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RewardedRepositoryTest {

    @Autowired
    private RewardedRepository rewardedRepository;

    @Test
    void saveShouldSucceed() {
        var rewarded = new Rewarded().setRewardedId("rewardedId").setType(ACTOR);
        var saved = rewardedRepository.save(rewarded);
        var found = rewardedRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getId()).isNotNull();
        assertThat(found.get().getRewardedId()).isEqualTo("rewardedId");
        assertThat(found.get().getType()).isEqualTo(ACTOR);
        assertThat(found.get().getRewards()).isEmpty();
    }
}
