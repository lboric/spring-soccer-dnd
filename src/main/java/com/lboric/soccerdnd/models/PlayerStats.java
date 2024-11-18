package com.lboric.soccerdnd.models;

import com.lboric.soccerdnd.dtos.PlayerStatsDTO;
import com.lboric.soccerdnd.entities.PlayerEntity;
import com.lboric.soccerdnd.entities.PlayerStatsEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model representing the statistics of a player in the system.
 *
 * <p>
 * This class contains the player's statistics such as season year and the number of goals scored.
 * It is used for transferring player statistics data within the system and for converting to
 * both a {@link PlayerStatsDTO} (Data Transfer Object) and a {@link PlayerStatsEntity} (for persistence).
 * </p>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerStats {

    /**
     * The unique identifier for the player associated with these statistics.
     * This is typically set when the player is retrieved from the system.
     */
    private Long playerId;

    /**
     * The first name of the player. This is used to identify the player along with their surname.
     */
    private String name;

    /**
     * The last name of the player. This is used to identify the player along with their first name.
     */
    private String surname;

    /**
     * The season year for which the player statistics are recorded.
     * The value represents the year of the season, typically used for historical data.
     */
    private int seasonYear;

    /**
     * The number of goals scored by the player in the specified season.
     * This represents the player’s performance in terms of scoring goals.
     */
    private int numberOfGoals;

    /**
     * Converts this {@link PlayerStats} object to a {@link PlayerStatsDTO} for data transfer.
     *
     * <p>
     * This method is used to prepare the statistics data for transferring via API or presenting to the client.
     * </p>
     *
     * @return a {@link PlayerStatsDTO} object containing the player statistics data
     */
    public PlayerStatsDTO toDTO() {
        return new PlayerStatsDTO(this.playerId, this.name, this.surname, this.seasonYear, this.numberOfGoals);
    }

    /**
     * Converts this {@link PlayerStats} object to a {@link PlayerStatsEntity} for persistence in the database.
     *
     * <p>
     * This method is used to convert the model into an entity suitable for storing player statistics in the database.
     * </p>
     *
     * @param playerEntity the {@link PlayerEntity} associated with the player for whom the statistics are being saved
     * @return a {@link PlayerStatsEntity} representing this player’s statistics for persistence
     */
    public PlayerStatsEntity toEntity(final PlayerEntity playerEntity) {
        return PlayerStatsEntity.builder()
           .id(this.playerId)
           .player(playerEntity)
           .seasonYear(this.seasonYear)
           .numberOfGoals(this.numberOfGoals)
           .build();
    }

}
