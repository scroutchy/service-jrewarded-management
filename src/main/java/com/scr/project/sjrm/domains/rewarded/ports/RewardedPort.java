package com.scr.project.sjrm.domains.rewarded.ports;

import com.scr.project.sjrm.domains.rewarded.model.entity.Rewarded;

public interface RewardedPort {

    Rewarded create(Rewarded rewarded);
}
