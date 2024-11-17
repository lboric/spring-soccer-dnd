package com.lboric.soccerdnd.services;

import java.util.Set;

import com.lboric.soccerdnd.models.PlayerStats;

public interface PlayerStatsService {

    PlayerStats getPlayerStatsById(Long id);

    Set<PlayerStats> getAllPlayersStats();

    PlayerStats addPlayerStats(PlayerStats playerStats);

    PlayerStats updatePlayerStats(PlayerStats playerStats);

    void deletePlayerStatsByNameSurnameAndSeasonYear(PlayerStats playerStats);

}
