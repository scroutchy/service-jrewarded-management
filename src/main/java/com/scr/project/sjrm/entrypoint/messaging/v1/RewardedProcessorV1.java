package com.scr.project.sjrm.entrypoint.messaging.v1;

import com.scr.project.sjrm.domains.rewarded.service.RewardedService;
import com.scr.project.sjrm.entrypoint.mapper.RewardedMappings;
import com.scr.project.srm.RewardedKafkaDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class RewardedProcessorV1 {

    private static final Logger LOGGER = LoggerFactory.getLogger(RewardedProcessorV1.class);

    private final RewardedService rewardedService;

    public RewardedProcessorV1(RewardedService rewardedService) {
        this.rewardedService = rewardedService;
    }

    @KafkaListener(topics = "${messaging.topics.rewarded-creation-notification}", groupId = "${messaging.group-id}")
    public void consume(RewardedKafkaDto message) {
        var rewarded = RewardedMappings.toEntity(message);
        rewardedService.save(rewarded);
        LOGGER.info("RewardedKafkaDto message consumed: {}", message);
    }

}
