package com.scr.project.sjrm.entrypoint.model.api;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class RewardApiDto {

    private Long id;

    @NotBlank
    private String type;

    @Min(1900)
    private int year;

    @NotBlank
    private String category;
}
