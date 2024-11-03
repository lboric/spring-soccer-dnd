package com.lboric.soccerdnd.entities;

import com.lboric.soccerdnd.models.Player;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "PLAYER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String surname;

    public Player toModel() {
        return Player.builder()
            .id(this.id)
            .name(this.name)
            .surname(this.surname)
            .build();
    }

}
