package com.lboric.soccerdnd.unit.services;

import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lboric.soccerdnd.models.PlayerStats;
import com.lboric.soccerdnd.services.PlayerStatsService;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class PlayerStatsServiceTest {

    private final PlayerStatsService playerStatsService;

    @Autowired
    PlayerStatsServiceTest(final PlayerStatsService playerStatsService) {
        this.playerStatsService = playerStatsService;
    }

    @Test
    @DisplayName("GIVEN stats for three players were recorded, WHEN playerStatsService.getAll is called, THEN it should return stats for players")
    void testGetAllPlayerStats() {
        final Set<PlayerStats> playersStats = this.playerStatsService.getAllPlayersStats();

        assertAll(
            () -> assertNotNull(playersStats, "Set of player stats should not be null"),
            () -> assertFalse(playersStats.isEmpty(), "Set of player stats should not be empty"),
            () -> assertEquals(3, playersStats.size(), "List of player stats should have 3 entries"),
            () -> assertTrue(
              playersStats.contains(PlayerStats.builder().id(1L).name("test").surname("player1").seasonYear(2024).numberOfGoals(17).build()),
        "Player stats should contain player with Id 1, 2024 season, and 17 goals"
            ),
            () -> assertTrue(
              playersStats.contains(PlayerStats.builder().id(2L).name("test").surname("player2").seasonYear(2024).numberOfGoals(21).build()),
        "Player stats should contain player with Id 2, 2024 season, and 21 goals"
            ),
            () -> assertTrue(
              playersStats.contains(PlayerStats.builder().id(3L).name("test").surname("player3").seasonYear(2024).numberOfGoals(32).build()),
        "Player stats should contain player with Id 3, 2024 season, and 32 goals"
            )
        );
    }

    @Test
    @DisplayName("GIVEN player id, WHEN playerStatsService.getPlayerStatsById is called, THEN it should return stats for that player")
    void testGetPlayerStatsById() {
        final PlayerStats playerStats = this.playerStatsService.getPlayerStatsById(2L);

        assertAll(
            () -> assertNotNull(playerStats, "Player stats object should not be null"),
            () -> assertEquals(playerStats, PlayerStats.builder().id(2L).name("test").surname("player2").seasonYear(2024).numberOfGoals(21).build(),
            "Player stats should contain player with ID 2, 2024 season, and 21 goals")
        );
    }

}
