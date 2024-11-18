package com.lboric.soccerdnd.dtos;

import com.lboric.soccerdnd.models.PlayerStats;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PlayerStatsDTO(

    @Nullable
    Long playerId,

    @NotNull(message = "Name is required.")
    String name,

    @NotNull(message = "Surname is required.")
    String surname,

    @Min(value = 0, message = "seasonYear must be positive number.")
    @Max(value = 2030, message = "Latest allowed year is 2030.")
    int seasonYear,

    @Min(value = 0, message = "numberOfGoals must be positive number")
    int numberOfGoals) {

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
