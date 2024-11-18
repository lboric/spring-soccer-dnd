package com.lboric.soccerdnd.models;

import com.lboric.soccerdnd.dtos.PlayerDTO;
import com.lboric.soccerdnd.entities.PlayerEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model representing a player in the system.
 *
 * <p>
 * This class holds the player information such as ID, name, and surname. It serves as the
 * domain model for the application, providing methods to convert the player data to
 * both a {@link PlayerDTO} for data transfer and a {@link PlayerEntity} for persistence in the database.
 * </p>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Player {

    /**
     * The unique identifier for the player.
     * This is usually set by the database when the player is persisted.
     */
    private Long id;

    /**
     * The first name of the player.
     * This field cannot be null and represents the player’s given name.
     */
    private String name;

    /**
     * The last name of the player.
     * This field cannot be null and represents the player’s family name.
     */
    private String surname;

    /**
     * Converts this {@link Player} object to a {@link PlayerDTO} for data transfer.
     *
     * <p>
     * This method is useful for converting the player model to a format suitable for
     * returning from API endpoints or for use in UI layers.
     * </p>
     *
     * @return a {@link PlayerDTO} containing the player’s data
     */
    public PlayerDTO toDTO() {
        return new PlayerDTO(this.id, this.name, this.surname);
    }

    /**
     * Converts this {@link Player} object to a {@link PlayerEntity} for persistence.
     *
     * <p>
     * This method is used to convert the model into an entity that can be persisted in the database.
     * </p>
     *
     * @return a {@link PlayerEntity} representing this player in the database
     */
    public PlayerEntity toEntity() {
        return PlayerEntity.builder()
            .id(this.id)
            .name(this.name)
            .surname(this.surname)
            .build();
    }

}
