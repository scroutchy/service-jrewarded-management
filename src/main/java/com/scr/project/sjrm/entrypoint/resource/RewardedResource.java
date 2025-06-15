package com.scr.project.sjrm.entrypoint.resource;

import com.scr.project.sjrm.domains.rewarded.service.RewardService;
import com.scr.project.sjrm.domains.rewarded.service.RewardedService;
import com.scr.project.sjrm.entrypoint.mapper.RewardedMappings;
import com.scr.project.sjrm.entrypoint.model.api.RewardApiDto;
import com.scr.project.sjrm.entrypoint.model.api.RewardedApiDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/rewarded")
public class RewardedResource {

    private final RewardedService rewardedService;

    private final RewardService rewardService;

    public RewardedResource(RewardedService rewardedService, RewardService rewardService) {
        this.rewardedService = rewardedService;
        this.rewardService = rewardService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RewardedApiDto> find(@PathVariable Long id) {
        return rewardedService.findBy(id)
                              .map(RewardedMappings::toApiDto)
                              .map(ResponseEntity::ok)
                              .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}")
    public ResponseEntity<RewardedApiDto> addReward(@RequestBody RewardApiDto rewardDto, @PathVariable Long id) {
        var reward = RewardedMappings.toEntity(rewardDto);
        return rewardService.addReward(reward, id)
                            .map(RewardedMappings::toApiDto)
                            .map(ResponseEntity::ok)
                            .orElse(ResponseEntity.notFound().build());
    }

}
