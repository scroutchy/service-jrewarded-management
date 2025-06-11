package com.scr.project.sjrm.domains.rewarded.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "rewards")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Reward {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private String category;

    @ManyToOne
    private Rewarded rewarded;
}
