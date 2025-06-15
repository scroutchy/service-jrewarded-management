package com.scr.project.sjrm;

import com.scr.project.sjrm.domains.rewarded.model.entity.Reward;
import com.scr.project.sjrm.domains.rewarded.model.entity.Rewarded;
import com.scr.project.sjrm.domains.rewarded.repository.RewardedRepository;
import jakarta.persistence.EntityManager;

import static com.scr.project.sjrm.domains.rewarded.model.entity.RewardedType.ACTOR;

public class RewardedTestDataUtil {

    public static void initTestData(EntityManager entityManager, RewardedRepository rewardedRepository) {
        rewardedRepository.deleteAll();
        Rewarded rewarded1 = new Rewarded().setType(ACTOR).setRewardedId("rewardedId1");
        Rewarded rewarded2 = new Rewarded().setType(ACTOR).setRewardedId("rewardedId2");
        entityManager.persist(rewarded1);
        entityManager.persist(rewarded2);
        Reward reward1 = new Reward().setType("Oscar").setYear(2020).setCategory("Best Actor");
        Reward reward2 = new Reward().setType("Golden Globe").setYear(2021).setCategory("Best Actor");
        entityManager.persist(reward1);
        entityManager.persist(reward2);
        rewarded2.getRewards().add(reward1);
        rewarded2.getRewards().add(reward2);
        entityManager.flush();
    }

}
