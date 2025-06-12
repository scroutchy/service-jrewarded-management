package com.scr.project.sjrm.domains.rewarded.service;

import com.scr.project.sjrm.domains.rewarded.model.entity.Rewarded;
import com.scr.project.sjrm.domains.rewarded.repository.RewardedRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.scr.project.sjrm.domains.rewarded.model.entity.RewardedType.ACTOR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RewardedServiceTest {

    @Mock
    private RewardedRepository rewardedRepository;

    @InjectMocks
    private RewardedService rewardedService;

    @Test
    void createShouldSucceed() {
        var rewarded = new Rewarded().setRewardedId("rewardedId").setType(ACTOR);
        when(rewardedRepository.save(rewarded)).thenReturn(rewarded.setId(1L));
        var result = rewardedService.create(rewarded);
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getRewardedId()).isEqualTo("rewardedId");
        assertThat(result.getType()).isEqualTo(ACTOR);
        verify(rewardedRepository, times(1)).save(rewarded);
    }

}
