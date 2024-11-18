package com.lboric.soccerdnd.dtos;

import com.lboric.soccerdnd.models.Player;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object (DTO) representing a Player.
 *
 * <p>
 * This DTO is used to transfer player data between the API and the service layer.
 * It includes basic player details such as ID, name, and surname.
 * </p>
 *
 * <p>
 * Validation constraints are applied to ensure that required fields (name and surname)
 * are provided when creating or updating a player.
 * </p>
 */
public record PlayerDTO(

    @Nullable
    Long id,

    @NotNull(message = "Name is required.")
    String name,

    @NotNull(message = "Surname is required.")
    String surname) {

    /**
    * Converts this {@link PlayerDTO} to a {@link Player} model object.
    *
    * @return a {@link Player} object containing the data from this DTO
    */
    public Player toModel() {
        return Player.builder()
            .id(this.id)
            .name(this.name)
            .surname(this.surname)
            .build();
    }

}
