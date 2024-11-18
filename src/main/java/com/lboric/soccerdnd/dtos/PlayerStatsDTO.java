package com.lboric.soccerdnd.dtos;

import com.lboric.soccerdnd.models.PlayerStats;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object (DTO) representing player statistics.
 *
 * <p>
 * This DTO is used to transfer player statistics data between the API and the service layer.
 * It includes basic player stats such as the player's ID, name, surname, season year, and number of goals.
 * </p>
 *
 * <p>
 * Validation constraints are applied to ensure that required fields (name, surname, seasonYear, numberOfGoals)
 * are provided and have valid values when creating or updating player statistics.
 * </p>
 */
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

    /**
     * Converts this {@link PlayerStatsDTO} to a {@link PlayerStats} model object.
     *
     * @return a {@link PlayerStats} object containing the data from this DTO
     */
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
