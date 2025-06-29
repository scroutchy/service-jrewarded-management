package com.scr.project.sjrm.entrypoint.unit.mapper;

import com.scr.project.sjrm.domains.rewarded.model.entity.Reward;
import com.scr.project.sjrm.domains.rewarded.model.entity.Rewarded;
import com.scr.project.sjrm.entrypoint.mapper.RewardedMappings;
import com.scr.project.srm.RewardedEntityTypeKafkaDto;
import com.scr.project.srm.RewardedKafkaDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.scr.project.sjrm.domains.rewarded.model.entity.RewardedType.ACTOR;
import static org.assertj.core.api.Assertions.assertThat;

class RewardedMappingsTest {

    @Test
    void toEntityShouldSucceed() {
        var kafkaDto = new RewardedKafkaDto("rewardedId", RewardedEntityTypeKafkaDto.ACTOR);
        var rewarded = RewardedMappings.toEntity(kafkaDto);
        assertThat(rewarded.getId()).isNull();
        assertThat(rewarded.getRewardedId()).isEqualTo(kafkaDto.getId());
        assertThat(rewarded.getType().name()).isEqualTo(kafkaDto.getType().name());
    }

    @Test
    void toApiDtoShouldSucceed() {
        var rewarded = new Rewarded().setId(1L)
                                     .setRewardedId("reawardedId")
                                     .setType(ACTOR)
                                     .setRewards(List.of(
                                             new Reward().setId(1L).setYear(2000).setCategory("category").setType("type")
                                     ));
        var apiDto = RewardedMappings.toApiDto(rewarded);
        assertThat(apiDto.getId()).isEqualTo(rewarded.getId());
        assertThat(apiDto.getRewardedId()).isEqualTo(rewarded.getRewardedId());
        assertThat(apiDto.getType()).isEqualTo(rewarded.getType());
        assertThat(apiDto.getRewards()).hasSize(1);
        assertThat(apiDto.getRewards().get(0).getId()).isEqualTo(rewarded.getRewards().get(0).getId());
    }
}
