package com.scr.project.sjrm.entrypoint.model.api;

import com.scr.project.sjrm.domains.rewarded.model.entity.RewardedType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class RewardedApiDto {

    private Long id;

    @NotBlank
    private String rewardedId;

    @NotNull
    private RewardedType type;

    private List<RewardApiDto> rewards = new ArrayList<>();

}
