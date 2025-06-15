package com.scr.project.sjrm.entrypoint.mapper;

import com.scr.project.sjrm.domains.rewarded.model.entity.Reward;
import com.scr.project.sjrm.domains.rewarded.model.entity.Rewarded;
import com.scr.project.sjrm.domains.rewarded.model.entity.RewardedType;
import com.scr.project.sjrm.entrypoint.model.api.RewardApiDto;
import com.scr.project.sjrm.entrypoint.model.api.RewardedApiDto;
import com.scr.project.srm.RewardedKafkaDto;

public final class RewardedMappings {

    private RewardedMappings() {
    }

    public static Rewarded toEntity(RewardedKafkaDto dto) {
        return new Rewarded()
                .setRewardedId(dto.getId())
                .setType(RewardedType.valueOf(dto.getType().name()));
    }

    public static RewardedApiDto toApiDto(Rewarded entity) {
        return new RewardedApiDto()
                .setId(entity.getId())
                .setRewardedId(entity.getRewardedId())
                .setType(entity.getType())
                .setRewards(entity.getRewards().stream().map(RewardedMappings::toApiDto).toList());
    }

    public static Reward toEntity(RewardApiDto dto) {
        return new Reward()
                .setId(dto.getId())
                .setCategory(dto.getCategory())
                .setYear(dto.getYear())
                .setType(dto.getType());
    }

    private static RewardApiDto toApiDto(Reward entity) {
        return new RewardApiDto()
                .setId(entity.getId())
                .setCategory(entity.getCategory())
                .setYear(entity.getYear())
                .setType(entity.getType());
    }
}
