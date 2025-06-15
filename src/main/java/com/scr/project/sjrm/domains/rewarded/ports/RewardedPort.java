package com.scr.project.sjrm.domains.rewarded.ports;

import com.scr.project.sjrm.domains.rewarded.model.entity.Rewarded;

import java.util.Optional;

public interface RewardedPort {

    Rewarded create(Rewarded rewarded);

    Optional<Rewarded> findBy(Long id);
}
