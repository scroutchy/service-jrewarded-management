package com.scr.project.sjrm.domains.rewarded.repository;

import com.scr.project.sjrm.domains.rewarded.model.entity.Reward;
import com.scr.project.sjrm.domains.rewarded.model.entity.Rewarded;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

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

    @Test
    void saveRewardedWithRewardsShouldSucceed() {
        var reward1 = new Reward().setType("Oscar").setYear(2020).setCategory("Best Actor");
        var reward2 = new Reward().setType("Golden Globe").setYear(2021).setCategory("Best Actor");
        var rewarded = new Rewarded().setRewardedId("rewardedId").setType(ACTOR).setRewards(List.of(reward1, reward2));
        var saved = rewardedRepository.save(rewarded);
        var found = rewardedRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getRewards()).hasSize(2);
        assertThat(found.get().getRewards().get(0).getType()).isEqualTo("Oscar");
        assertThat(found.get().getRewards().get(0).getYear()).isEqualTo(2020);
        assertThat(found.get().getRewards().get(0).getCategory()).isEqualTo("Best Actor");
        assertThat(found.get().getRewards().get(1).getType()).isEqualTo("Golden Globe");
        assertThat(found.get().getRewards().get(1).getYear()).isEqualTo(2021);
        assertThat(found.get().getRewards().get(1).getCategory()).isEqualTo("Best Actor");
    }

}
