package com.scr.project.sjrm.entrypoint.model.api;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class RewardApiDto {

    private Long id;

    private String type;

    private int year;

    private String category;
}
