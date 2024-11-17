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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "player_stats", uniqueConstraints = @UniqueConstraint(columnNames = {"player_id", "season_year"}))
public class PlayerStatsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private PlayerEntity player;

    @Column(name = "season_year")
    private int seasonYear;

    @Column(name = "number_of_goals")
    private int numberOfGoals;

    public PlayerStats toModel() {
        return PlayerStats.builder()
            .playerId(this.player.getId())
            .name(this.player.getName())
            .surname(this.player.getSurname())
            .seasonYear(this.seasonYear)
            .numberOfGoals(this.numberOfGoals)
            .build();
    }

}
