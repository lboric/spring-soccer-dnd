package com.lboric.soccerdnd.dtos;

import com.lboric.soccerdnd.models.Player;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

public record PlayerDTO(

    @Nullable
    Long id,

    @NotNull(message = "Name is required.")
    String name,

    @NotNull(message = "Surname is required.")
    String surname) {

    public Player toModel() {
        return Player.builder()
            .id(this.id)
            .name(this.name)
            .surname(this.surname)
            .build();
    }

}
