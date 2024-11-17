package com.lboric.soccerdnd.unit.services;

import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.lboric.soccerdnd.exceptions.PlayerNotFoundException;
import com.lboric.soccerdnd.exceptions.PlayerStatsAlreadyExistsException;
import com.lboric.soccerdnd.exceptions.PlayerStatsNotFoundException;
import com.lboric.soccerdnd.models.PlayerStats;
import com.lboric.soccerdnd.services.PlayerStatsService;

import jakarta.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PlayerStatsServiceTest {

    private final PlayerStatsService playerStatsService;

    @Autowired
    PlayerStatsServiceTest(final PlayerStatsService playerStatsService) {
        this.playerStatsService = playerStatsService;
    }

    @Test
    @Order(1)
    @DisplayName("GIVEN stats for three players were recorded, WHEN playerStatsService.getAll is called, THEN it should return stats for players")
    void testGetAllPlayerStats() {
        final Set<PlayerStats> playersStats = this.playerStatsService.getAllPlayersStats();

        assertAll(
            () -> assertNotNull(playersStats, "Set of player stats should not be null"),
            () -> assertFalse(playersStats.isEmpty(), "Set of player stats should not be empty"),
            () -> assertEquals(3, playersStats.size(), "List of player stats should have 3 entries"),
            () -> assertTrue(
              playersStats.contains(PlayerStats.builder().playerId(1L).name("test").surname("player1").seasonYear(2024).numberOfGoals(17).build()),
        "Player stats should contain player with Id 1, 2024 season, and 17 goals"
            ),
            () -> assertTrue(
              playersStats.contains(PlayerStats.builder().playerId(2L).name("test").surname("player2").seasonYear(2024).numberOfGoals(21).build()),
        "Player stats should contain player with Id 2, 2024 season, and 21 goals"
            ),
            () -> assertTrue(
              playersStats.contains(PlayerStats.builder().playerId(3L).name("test").surname("player3").seasonYear(2024).numberOfGoals(32).build()),
        "Player stats should contain player with Id 3, 2024 season, and 32 goals"
            )
        );
    }

    @ParameterizedTest
    @Order(2)
    @MethodSource("com.lboric.soccerdnd.utils.ServiceTestUtils#provideExistingPlayersStats")
    @DisplayName("GIVEN player id, WHEN playerStatsService.getPlayerStatsById is called, THEN it should return stats for that player")
    void testGetPlayerStatsById(final Long id, final String expectedName, final String expectedSurname, final int seasonYear, final int numberOfGoals) {
        final PlayerStats playerStats = this.playerStatsService.getPlayerStatsById(id);

        assertAll(
            () -> assertNotNull(playerStats, "Player stats object should not be null"),
            () -> assertEquals(playerStats, PlayerStats.builder().playerId(id).name(expectedName).surname(expectedSurname).seasonYear(seasonYear).numberOfGoals(numberOfGoals).build(),
            "Player stats should match.")
        );
    }

    @Test
    @Order(3)
    @DisplayName("GIVEN a non-existent player ID, WHEN playerStatsService.getPlayerStatsById is called, THEN it should throw PlayerStatsNotFoundException")
    void testGetNonExistentPlayerStatsById() {
        final Long nonExistingPlayerId = 4L;

        assertThrows(PlayerStatsNotFoundException.class, () -> this.playerStatsService.getPlayerStatsById(nonExistingPlayerId));
    }

    @ParameterizedTest
    @Order(4)
    @Rollback
    @Transactional
    @MethodSource("com.lboric.soccerdnd.utils.ServiceTestUtils#providePlayersStatsToAdd")
    @DisplayName("GIVEN a player stats is added, WHEN playerStatsService.addPlayerStats is called, THEN it should return the added player stats")
    void testAddPlayerStats(final PlayerStats expectedPlayerStats) {
        final PlayerStats addedPlayerStats = this.playerStatsService.addPlayerStats(expectedPlayerStats);

        assertAll(
          () -> assertNotNull(expectedPlayerStats, "Player stats object should not be null"),
          () -> assertEquals(expectedPlayerStats, addedPlayerStats, "Player stats should match.")
        );
    }

    @Test
    @Order(5)
    @DisplayName("GIVEN a player stats is added but record for that year already exists, WHEN playerService.addPlayer is called, THEN it should throw PlayerStatsAlreadyExistsException")
    void testAddPlayerStatsForExistingSeasonYear() {
        final PlayerStats existantPlayerStats = PlayerStats.builder()
            .playerId(1L)
            .name("test")
            .surname("player1")
            .seasonYear(2024)
            .numberOfGoals(1)
            .build();

        assertThrows(PlayerStatsAlreadyExistsException.class, () -> this.playerStatsService.addPlayerStats(existantPlayerStats));
    }

    @ParameterizedTest
    @Order(6)
    @Rollback
    @Transactional
    @MethodSource("com.lboric.soccerdnd.utils.ServiceTestUtils#provideUpdatedPlayersStats")
    @DisplayName("GIVEN a player stats is updated, WHEN playerStatsService.updatePlayerStats is called, THEN it should return the updated player stats")
    void testUpdatePlayerStats(final PlayerStats expectedPlayerStats) {
        final PlayerStats beforeUpdatePlayerStats = this.playerStatsService.getPlayerStatsById(expectedPlayerStats.getPlayerId());
        final PlayerStats updatedPlayerStats = this.playerStatsService.updatePlayerStats(expectedPlayerStats);

        assertAll(
          () -> assertNotNull(expectedPlayerStats, "Player stats object should not be null"),
          () -> assertNotEquals(beforeUpdatePlayerStats, updatedPlayerStats, "Player stats should not match."),
          () -> assertEquals(expectedPlayerStats, updatedPlayerStats, "Player stats should match.")
        );
    }

    @Test
    @Order(7)
    @DisplayName("GIVEN a player stats is updated for non-existent player, WHEN playerStatsService.updatePlayerStats is called, THEN it should throw PlayerNotFoundException")
    void testUpdateNonExistentPlayerStats() {
        final PlayerStats nonExistentPlayerStats = PlayerStats.builder()
            .playerId(4L)
            .name("test")
            .surname("player4")
            .seasonYear(2024)
            .numberOfGoals(1)
            .build();

        assertThrows(PlayerNotFoundException.class, () -> this.playerStatsService.updatePlayerStats(nonExistentPlayerStats));
    }

    @Test
    @Order(8)
    @DisplayName("GIVEN a player stats is updated for non-existent record, WHEN playerStatsService.updatePlayerStats is called, THEN it should throw PlayerStatsNotFoundException")
    void testUpdateNonExistentPlayerStatsRecord() {
        final PlayerStats nonExistentPlayerStats = PlayerStats.builder()
            .playerId(1L)
            .name("test")
            .surname("player1")
            .seasonYear(2025)
            .numberOfGoals(1)
            .build();

        assertThrows(PlayerStatsNotFoundException.class, () -> this.playerStatsService.updatePlayerStats(nonExistentPlayerStats));
    }

    @ParameterizedTest
    @Order(9)
    @Rollback
    @Transactional
    @MethodSource("com.lboric.soccerdnd.utils.ServiceTestUtils#providePlayersStatsToDelete")
    @DisplayName("GIVEN a player stats is deleted, WHEN playerStatsService.deletePlayerStatsByNameSurnameAndSeasonYear is called, THEN it should delete the player")
    void testDeletePlayerStatsByNameAndSurname(final PlayerStats playerStatsToDelete) {
        this.playerStatsService.deletePlayerStatsByNameSurnameAndSeasonYear(playerStatsToDelete);

        assertThrows(PlayerStatsNotFoundException.class, () -> this.playerStatsService.getPlayerStatsById(playerStatsToDelete.getPlayerId()));
    }

    @Test
    @Order(10)
    @DisplayName("GIVEN a player stats is trying to be deleted but doesn't exist, WHEN playerStatsService.deletePlayerStatsByNameSurnameAndSeasonYear is called, THEN it should throw PlayerStatsNotFoundException")
    void testDeletePlayerStatsByNonExistingNameAndSurname() {
        final PlayerStats nonExistentPlayerStats = PlayerStats.builder()
            .playerId(1L)
            .name("test")
            .surname("player1")
            .seasonYear(2025)
            .numberOfGoals(1)
            .build();

        assertThrows(PlayerStatsNotFoundException.class, () -> this.playerStatsService.deletePlayerStatsByNameSurnameAndSeasonYear(nonExistentPlayerStats));
    }

}
