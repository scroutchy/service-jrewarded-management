package com.scr.project.sjrm.entrypoint.unit.mapper;

import com.scr.project.sjrm.entrypoint.mapper.RewardedMappings;
import com.scr.project.srm.RewardedEntityTypeKafkaDto;
import com.scr.project.srm.RewardedKafkaDto;
import org.junit.jupiter.api.Test;

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
}
