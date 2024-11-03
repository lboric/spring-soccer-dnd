package com.lboric.soccerdnd.entities;

import com.lboric.soccerdnd.models.PlayerStats;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "PLAYER_STATS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"PLAYER_ID", "SEASON_YEAR"}))
public class PlayerStatsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PLAYER_ID")
    private PlayerEntity player;

    @Column(name = "SEASON_YEAR", nullable = false)
    private int seasonYear;

    private int numberOfGoals;

    public PlayerStats toModel() {
        return PlayerStats.builder()
            .id(this.id)
            .numberOfGoals(this.numberOfGoals)
            .build();
    }

}
