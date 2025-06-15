package com.scr.project.sjrm.entrypoint.unit.resource;

import com.scr.project.sjrm.domains.rewarded.model.entity.Rewarded;
import com.scr.project.sjrm.domains.rewarded.service.RewardedService;
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
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(MockitoExtension.class)
class RewardedResourceTest {

    @Mock
    private RewardedService rewardedService;

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

}
