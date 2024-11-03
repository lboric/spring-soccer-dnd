package com.lboric.soccerdnd.models;

import com.lboric.soccerdnd.dtos.PlayerStatsDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerStats {

    private long id;

    private String name;

    private String surname;

    private int seasonYear;

    private int numberOfGoals;

    public PlayerStatsDTO toDTO() {
        return new PlayerStatsDTO(this.id, this.name, this.surname, this.seasonYear, this.numberOfGoals);
    }

}
