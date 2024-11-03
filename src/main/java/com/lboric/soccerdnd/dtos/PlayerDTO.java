package com.lboric.soccerdnd.dtos;

import com.lboric.soccerdnd.models.Player;

public record PlayerDTO(long id, String name, String surname) {

    public Player toModel() {
        return Player.builder()
            .id(this.id)
            .name(this.name)
            .surname(this.surname)
            .build();
    }

}
