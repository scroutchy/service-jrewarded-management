package com.scr.project.sjrm.entrypoint.unit.messaging.v1;

import com.scr.project.sjrm.domains.rewarded.service.RewardedService;
import com.scr.project.sjrm.entrypoint.mapper.RewardedMappings;
import com.scr.project.sjrm.entrypoint.messaging.v1.RewardedProcessorV1;
import com.scr.project.srm.RewardedKafkaDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.scr.project.srm.RewardedEntityTypeKafkaDto.ACTOR;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RewardedProcessorV1Test {

    @Mock
    private RewardedService rewardedService;

    @InjectMocks
    private RewardedProcessorV1 rewardedProcessorV1;

    @Test
    void consumeShouldSucceedAndCallRewardedService() {
        var kafkaDto = new RewardedKafkaDto("rewardedId", ACTOR);
        when(rewardedService.save(any())).thenAnswer(i -> i.getArgument(0));
        rewardedProcessorV1.consume(kafkaDto);
        verify(rewardedService, times(1)).save(RewardedMappings.toEntity(kafkaDto));
    }

}
