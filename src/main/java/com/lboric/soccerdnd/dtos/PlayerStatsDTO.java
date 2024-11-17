package com.lboric.soccerdnd.dtos;

import com.lboric.soccerdnd.models.PlayerStats;

public record PlayerStatsDTO(long playerId, String name, String surname, int seasonYear, int numberOfGoals) {

    public PlayerStats toModel() {
        return PlayerStats.builder()
            .playerId(this.playerId)
            .name(this.name)
            .surname(this.surname)
            .seasonYear(this.seasonYear())
            .numberOfGoals(this.numberOfGoals)
            .build();
    }

}
