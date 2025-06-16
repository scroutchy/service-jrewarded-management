package com.scr.project.sjrm.entrypoint.unit.resource;

import com.scr.project.sjrm.domains.rewarded.model.entity.Rewarded;
import com.scr.project.sjrm.domains.rewarded.service.RewardService;
import com.scr.project.sjrm.domains.rewarded.service.RewardedService;
import com.scr.project.sjrm.entrypoint.mapper.RewardedMappings;
import com.scr.project.sjrm.entrypoint.model.api.RewardApiDto;
import com.scr.project.sjrm.entrypoint.resource.RewardedResource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.scr.project.sjrm.domains.rewarded.model.entity.RewardedType.ACTOR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(MockitoExtension.class)
class RewardedResourceTest {

    @Mock
    private RewardedService rewardedService;

    @Mock
    private RewardService rewardService;

    @InjectMocks
    private RewardedResource rewardedResource;

    @Test
    void findShouldSucceed() {
        when(rewardedService.findBy(1L))
                .thenReturn(Optional.of(new Rewarded(1L, "rewarded", ACTOR, List.of())));
        var result = rewardedResource.find(1L);
        assertThat(result.getStatusCode()).isEqualTo(OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getId()).isEqualTo(1L);
        assertThat(result.getBody().getRewardedId()).isEqualTo("rewarded");
        assertThat(result.getBody().getType()).isEqualTo(ACTOR);
        assertThat(result.getBody().getRewards()).isEmpty();
        verify(rewardedService, times(1)).findBy(1L);
    }

    @Test
    void findShouldReturnNotFoundWhenRewardedDoesNotExist() {
        when(rewardedService.findBy(1L)).thenReturn(Optional.empty());
        var result = rewardedResource.find(1L);
        assertThat(result.getStatusCode()).isEqualTo(NOT_FOUND);
        verify(rewardedService, times(1)).findBy(1L);
    }

    @Test
    void addRewardShouldSucceed() {
        var newRewardDto = new RewardApiDto().setCategory("Best actor").setType("Oscar").setYear(2020);
        var rewarded = new Rewarded(1L, "rewardedId", ACTOR, List.of());
        when(rewardService.addReward(RewardedMappings.toEntity(newRewardDto), 1L)).thenReturn(
                Optional.of(rewarded.setRewards(List.of(RewardedMappings.toEntity(newRewardDto).setId(1L)))));
        var result = rewardedResource.addReward(newRewardDto, 1L);
        assertThat(result.getStatusCode()).isEqualTo(CREATED);
        var body = result.getBody();
        assertThat(body).isNotNull();
        assertThat(body.getRewards()).hasSize(1);
        var rew = body.getRewards().get(0);
        assertThat(rew.getId()).isEqualTo(1L);
        assertThat(rew.getCategory()).isEqualTo("Best actor");
        assertThat(rew.getType()).isEqualTo("Oscar");
        assertThat(rew.getYear()).isEqualTo(2020);
    }

    @Test
    void addRewardShouldReturnNotFoundWhenRewardServiceReturnsEmpty() {
        var newRewardDto = new RewardApiDto().setCategory("Best actor").setType("Oscar").setYear(2020);
        when(rewardService.addReward(RewardedMappings.toEntity(newRewardDto), 1L)).thenReturn(
                Optional.empty());
        var result = rewardedResource.addReward(newRewardDto, 1L);
        assertThat(result.getStatusCode()).isEqualTo(NOT_FOUND);
    }
}
