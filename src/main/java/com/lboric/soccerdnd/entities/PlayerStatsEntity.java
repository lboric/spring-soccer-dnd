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

/**
 * Entity representing player statistics in the database.
 *
 * <p>
 * This entity maps player statistics such as the number of goals scored in a specific season.
 * It includes a reference to the associated {@link PlayerEntity} and maintains a unique constraint
 * on the combination of player ID and season year to ensure one entry per player per season.
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "player_stats", uniqueConstraints = @UniqueConstraint(columnNames = {"player_id", "season_year"}))
public class PlayerStatsEntity {

    /**
     * The unique identifier for the player statistics.
     * This field is generated automatically and serves as the primary key in the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The player to whom these statistics belong.
     * This relationship is a many-to-one association with the {@link PlayerEntity}.
     * It uses the {@link JoinColumn} annotation to map the player ID to the corresponding foreign key.
     */
    @ManyToOne
    @JoinColumn(name = "player_id")
    private PlayerEntity player;

    /**
     * The season year for which the statistics are recorded.
     * Each player can have statistics for different seasons.
     */
    @Column(name = "season_year")
    private int seasonYear;

    /**
     * The number of goals scored by the player in the given season.
     * This field represents the player's performance during the specific season.
     */
    @Column(name = "number_of_goals")
    private int numberOfGoals;

    /**
     * Converts this {@link PlayerStatsEntity} to a {@link PlayerStats} model object.
     *
     * <p>
     * This method transforms the entity into a domain model object, which is typically used in the service layer
     * or in data transfer objects (DTOs) for API communication.
     * </p>
     *
     * @return a {@link PlayerStats} object containing the data from this entity
     */
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
