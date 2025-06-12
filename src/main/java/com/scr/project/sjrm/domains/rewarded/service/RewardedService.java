package com.scr.project.sjrm.domains.rewarded.service;

import com.scr.project.sjrm.domains.rewarded.model.entity.Rewarded;
import com.scr.project.sjrm.domains.rewarded.ports.RewardedPort;
import com.scr.project.sjrm.domains.rewarded.repository.RewardedRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RewardedService implements RewardedPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(RewardedService.class);

    private final RewardedRepository rewardedRepository;

    public RewardedService(RewardedRepository rewardedRepository) {
        this.rewardedRepository = rewardedRepository;
    }

    @Override
    public Rewarded create(Rewarded rewarded) {
        LOGGER.debug("Creating rewarded entity with RewardedID: {}", rewarded.getRewardedId());
        var savedRewarded = rewardedRepository.save(rewarded);
        LOGGER.info("Created rewarded entity with RewardedID: {}", rewarded.getId());
        return savedRewarded;
    }
}
