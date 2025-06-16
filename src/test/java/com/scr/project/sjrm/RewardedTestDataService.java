package com.scr.project.sjrm;

import com.scr.project.sjrm.domains.rewarded.repository.RewardedRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Component
public class RewardedTestDataService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private RewardedRepository rewardedRepository;

    @Transactional(propagation = REQUIRES_NEW)
    public void initTestData() {
        RewardedTestDataUtil.initTestData(entityManager, rewardedRepository);
    }
}
