package com.lboric.soccerdnd.dtos;

import com.lboric.soccerdnd.models.PlayerStats;

public record PlayerStatsDTO(long id, String name, String surname, int seasonYear, int numberOfGoals) {

    public PlayerStats toModel() {
        return PlayerStats.builder()
            .id(this.id)
            .name(this.name)
            .surname(this.surname)
            .seasonYear(this.seasonYear())
            .numberOfGoals(this.numberOfGoals)
            .build();
    }

}
