package com.scr.project.sjrm.domains.rewarded.repository;

import com.scr.project.sjrm.domains.rewarded.model.entity.Rewarded;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardedRepository extends JpaRepository<Rewarded, Long> {

}
