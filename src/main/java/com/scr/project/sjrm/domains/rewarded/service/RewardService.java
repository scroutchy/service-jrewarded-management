package com.scr.project.sjrm.domains.rewarded.service;

import com.scr.project.sjrm.domains.rewarded.model.entity.Reward;
import com.scr.project.sjrm.domains.rewarded.model.entity.Rewarded;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RewardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RewardService.class);

    private final RewardedService rewardedService;

    public RewardService(RewardedService rewardedService) {
        this.rewardedService = rewardedService;
    }

    public Optional<Rewarded> addReward(Reward newReward, Long id) {
        LOGGER.debug("Adding reward to rewarded entity with ID: {}", id);
        return rewardedService.findBy(id).map(
                r -> {
                    r.getRewards().add(newReward);
                    var res = rewardedService.save(r);
                    LOGGER.info("Reward {} added to rewarded entity with ID: {}", newReward, id);
                    return res;
                }
        );
    }

}
