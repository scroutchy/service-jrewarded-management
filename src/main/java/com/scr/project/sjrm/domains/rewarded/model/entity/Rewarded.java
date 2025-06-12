package com.scr.project.sjrm.domains.rewarded.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "rewarded")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Rewarded {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "rewarded_id", nullable = false)
    private String rewardedId;

    @Enumerated(value = STRING)
    @Column(nullable = false)
    private RewardedType type;

    @OneToMany(mappedBy = "rewarded", cascade = PERSIST)
    private List<Reward> rewards = List.of();
}
