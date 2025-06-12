package com.scr.project.sjrm.entrypoint.mapper;

import com.scr.project.sjrm.domains.rewarded.model.entity.Rewarded;
import com.scr.project.sjrm.domains.rewarded.model.entity.RewardedType;
import com.scr.project.srm.RewardedKafkaDto;

public final class RewardedMappings {

    private RewardedMappings() {
    }

    public static Rewarded toEntity(RewardedKafkaDto dto) {
        return new Rewarded()
                .setRewardedId(dto.getId())
                .setType(RewardedType.valueOf(dto.getType().name()));
    }

}
