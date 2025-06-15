package com.scr.project.sjrm.entrypoint.integration.messaging.v1;

import com.scr.project.sjrm.AbstractIntegrationTest;
import com.scr.project.sjrm.KafkaTestProducerConfig;
import com.scr.project.sjrm.domains.rewarded.model.entity.Rewarded;
import com.scr.project.sjrm.domains.rewarded.model.entity.RewardedType;
import com.scr.project.sjrm.domains.rewarded.repository.RewardedRepository;
import com.scr.project.srm.RewardedKafkaDto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Example;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.annotation.Transactional;

import static com.scr.project.sjrm.TestExtensions.awaitUntil;
import static com.scr.project.srm.RewardedEntityTypeKafkaDto.ACTOR;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(KafkaTestProducerConfig.class)
class RewardedProcessorV1IntegrationTest extends AbstractIntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(RewardedProcessorV1IntegrationTest.class);

    @Autowired
    private RewardedRepository rewardedRepository;

    @Autowired
    private KafkaTemplate<String, RewardedKafkaDto> rewardedKafkaTemplate;

    @Test
    @Transactional
    void consumeShouldSuccessfullyProcessRewardedMessage() {
        var rewardedKafkaDto = new RewardedKafkaDto("rewardedId", ACTOR);
        try {
            var future = rewardedKafkaTemplate.send("srm-rewarded-entity-creation-events", rewardedKafkaDto);
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    logger.debug("Message successfully sent on Kafka : {}", rewardedKafkaDto);
                } else {
                    logger.error("Failure during sending of Kafka message : {}", ex.getMessage());
                }
            });
        } catch (Exception e) {
            logger.error("Failure during sending of Kafka message : {}", e.getMessage());
            throw e;
        }
        awaitUntil(() -> {
            var rewarded = rewardedRepository.findOne(Example.of(new Rewarded().setRewardedId("rewardedId")));
            assertThat(rewarded).isPresent();
            assertThat(rewarded.get().getId()).isNotNull();
            assertThat(rewarded.get().getRewardedId()).isEqualTo("rewardedId");
            assertThat(rewarded.get().getType()).isEqualTo(RewardedType.ACTOR);
            assertThat(rewarded.get().getRewards()).isEmpty();
        });

    }

}
