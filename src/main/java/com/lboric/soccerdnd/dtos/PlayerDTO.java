package com.lboric.soccerdnd.dtos;

import com.lboric.soccerdnd.models.Player;

import jakarta.annotation.Nullable;

public record PlayerDTO(@Nullable Long id, String name, String surname) {

    public Player toModel() {
        return Player.builder()
            .id(this.id)
            .name(this.name)
            .surname(this.surname)
            .build();
    }

}
