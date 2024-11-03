package com.lboric.soccerdnd.models;

import com.lboric.soccerdnd.dtos.PlayerDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Player {

    private long id;

    private String name;

    private String surname;

    public PlayerDTO toDTO() {
        return new PlayerDTO(this.id, this.name, this.surname);
    }

}
