package com.lboric.soccerdnd.models;

import com.lboric.soccerdnd.dtos.PlayerStatsDTO;
import com.lboric.soccerdnd.entities.PlayerEntity;
import com.lboric.soccerdnd.entities.PlayerStatsEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerStats {

    private long playerId;

    private String name;

    private String surname;

    private int seasonYear;

    private int numberOfGoals;

    public PlayerStatsDTO toDTO() {
        return new PlayerStatsDTO(this.playerId, this.name, this.surname, this.seasonYear, this.numberOfGoals);
    }

    public PlayerStatsEntity toEntity(final PlayerEntity playerEntity) {
        return PlayerStatsEntity.builder()
           .id(this.playerId)
           .player(playerEntity)
           .seasonYear(this.seasonYear)
           .numberOfGoals(this.numberOfGoals)
           .build();
    }

}
