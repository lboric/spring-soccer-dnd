package com.lboric.soccerdnd.models;

import com.lboric.soccerdnd.dtos.PlayerDTO;
import com.lboric.soccerdnd.entities.PlayerEntity;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Player {

    @Nullable
    private Long id;

    private String name;

    private String surname;

    public PlayerDTO toDTO() {
        return new PlayerDTO(this.id, this.name, this.surname);
    }

    public PlayerEntity toEntity() {
        return PlayerEntity.builder()
            .id(this.id)
            .name(this.name)
            .surname(this.surname)
            .build();
    }

}
