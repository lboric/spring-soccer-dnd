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

import com.lboric.soccerdnd.exceptions.PlayerAlreadyExistsException;
import com.lboric.soccerdnd.exceptions.PlayerNotFoundException;
import com.lboric.soccerdnd.models.Player;
import com.lboric.soccerdnd.services.PlayerService;

import jakarta.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PlayerServiceTest {

    private final PlayerService playerService;

    @Autowired
    PlayerServiceTest(final PlayerService playerService) {
        this.playerService = playerService;
    }

    @Test
    @Order(1)
    @DisplayName("GIVEN three players were recorded, WHEN playerService.getAllPlayers is called, THEN it should return the three player")
    void testGetAllPlayer() {
        final Set<Player> players = this.playerService.getAllPlayers();

        assertAll(
          () -> assertNotNull(players, "Player object should not be null"),
          () -> assertFalse(players.isEmpty(), "Players set should not be empty"),
          () -> assertEquals(3, players.size(), "List of players should have 3 entries"),
          () -> assertTrue(
            players.contains(Player.builder().id(1L).name("test").surname("player1").build()),
            "Player stats should contain player with Id 1, name 'test' and surname 'player1"
          ),
          () -> assertTrue(
            players.contains(Player.builder().id(2L).name("test").surname("player2").build()),
            "Player stats should contain player with Id 2, name 'test' and surname 'player2"
          ),
          () -> assertTrue(
            players.contains(Player.builder().id(3L).name("test").surname("player3").build()),
            "Player stats should contain player with Id 3, name 'test' and surname 'player3"
          )
        );
    }

    @ParameterizedTest
    @Order(2)
    @MethodSource("com.lboric.soccerdnd.utils.ServiceTestUtils#getPlayerByIdArgumentSet")
    @DisplayName("GIVEN an existing player ID, WHEN playerService.getPlayerById is called, THEN it should return the correct player")
    void testGetPlayerById(final Long id, final String expectedName, final String expectedSurname) {
        final Player player = this.playerService.getPlayerById(id);

        assertAll(
          () -> assertNotNull(player, "Player object should not be null"),
          () -> assertEquals(id, player.getId(), "Player ID should match"),
          () -> assertEquals(expectedName, player.getName(), "Player name should match"),
          () -> assertEquals(expectedSurname, player.getSurname(), "Player surname should match")
        );
    }

    @Test
    @Order(3)
    @DisplayName("GIVEN a non-existent player ID, WHEN playerService.getPlayerById is called, THEN it should throw PlayerNotFoundException")
    void testGetPlayerByNonExistentId() {
        final Long nonExistingPlayerId = 4L;

        assertThrows(PlayerNotFoundException.class, () -> this.playerService.getPlayerById(nonExistingPlayerId));
    }

    @ParameterizedTest
    @Order(3)
    @MethodSource("com.lboric.soccerdnd.utils.ServiceTestUtils#getPlayerByNameAndSurnameArgumentSet")
    @DisplayName("GIVEN an existing player, WHEN playerService.getPlayerByNameAndSurname is called, THEN it should return the correct player")
    void testGetPlayerByNameAndSurname(final String expectedName, final String expectedSurname) {
        final Player player = this.playerService.getPlayerByNameAndSurname(expectedName, expectedSurname);

        assertAll(
          () -> assertNotNull(player, "Player object should not be null"),
          () -> assertEquals(expectedName, player.getName(), "Player name should match"),
          () -> assertEquals(expectedSurname, player.getSurname(), "Player surname should match")
        );
    }

    @Test
    @Order(4)
    @DisplayName("GIVEN an non-existing player, WHEN playerService.testGetNonExistentPlayerByNameAndSurname is called, THEN it should throw PlayerNotFoundException")
    void testGetNonExistentPlayerByNameAndSurname() {
        final String name = "NON-EXISTENT";
        final String surname = "NON-EXISTENT";

        assertThrows(PlayerNotFoundException.class, () -> this.playerService.getPlayerByNameAndSurname(name, surname));
    }

    @ParameterizedTest
    @Order(5)
    @Rollback
    @Transactional
    @MethodSource("com.lboric.soccerdnd.utils.ServiceTestUtils#addPlayerArgumentSet")
    @DisplayName("GIVEN a player is added, WHEN playerService.addPlayer is called, THEN it should return the added player")
    void testAddPlayer(final Player expectedPlayer) {
        final Player addedPlayer = this.playerService.addPlayer(expectedPlayer);

        assertAll(
            () -> assertNotNull(addedPlayer, "Player object should not be null"),
            () -> assertEquals(expectedPlayer.getName(), addedPlayer.getName(), "Player name should match"),
            () -> assertEquals(expectedPlayer.getSurname(), addedPlayer.getSurname(), "Player surname should match")
        );
    }

    @Test
    @Order(6)
    @DisplayName("GIVEN a player is added but already exists, WHEN playerService.addPlayer is called, THEN it should throw PlayerAlreadyExistsException")
    void testAddPlayerButItAlreadyExists() {
        final Player existingPlayer = Player.builder().name("test").surname("player1").build();

        assertThrows(PlayerAlreadyExistsException.class, () -> this.playerService.addPlayer(existingPlayer));
    }

    @ParameterizedTest
    @Order(7)
    @Rollback
    @Transactional
    @MethodSource("com.lboric.soccerdnd.utils.ServiceTestUtils#updatePlayerArgumentSet")
    @DisplayName("GIVEN a player is updated, WHEN playerService.updatePlayer is called, THEN it should return the updated player")
    void testUpdatePlayer(final Player expectedPlayer) {
        final Player updatedPlayer = this.playerService.updatePlayer(expectedPlayer);

        assertAll(
            () -> assertNotNull(updatedPlayer, "Player object should not be null"),
            () -> assertEquals(expectedPlayer.getId(), updatedPlayer.getId(), "Player ID should remain the same"),
            () -> assertEquals(expectedPlayer.getName(), updatedPlayer.getName(), "Player name should match"),
            () -> assertEquals(expectedPlayer.getSurname(), updatedPlayer.getSurname(), "Player surname should match")
        );
    }

    @Test
    @Order(8)
    @DisplayName("GIVEN a non-existent player is trying to be updated, WHEN playerService.updatePlayer is called, THEN it should throw PlayerNotFoundException")
    void testUpdatePlayerWhichDoesntExist() {
        final Player nonExistantPlayer = Player.builder().id(10L).name("NON-EXISTENT").surname("NON-EXISTENT").build();

        assertThrows(PlayerNotFoundException.class, () ->  this.playerService.updatePlayer(nonExistantPlayer));
    }

    @ParameterizedTest
    @Order(9)
    @Rollback
    @Transactional
    @MethodSource("com.lboric.soccerdnd.utils.ServiceTestUtils#deletePlayersByIdArgumentSet")
    @DisplayName("GIVEN a player is deleted, WHEN playerService.deletePlayerById is called, THEN it should delete the player")
    void testDeletePlayerById(final Long id) {
        this.playerService.deletePlayerById(id);

        assertThrows(PlayerNotFoundException.class, () -> this.playerService.getPlayerById(id));
    }

    @Test
    @Order(10)
    @Rollback
    @Transactional
    @DisplayName("GIVEN multiple players are added, WHEN playerService.addMultiplePlayers is called, THEN it should return the added players")
    void testAddMultiplePlayers() {
        final Set<Player> playersToAdd = Set.of(
            Player.builder().name("test").surname("newPlayer1").build(),
            Player.builder().name("test").surname("newPlayer2").build(),
            Player.builder().name("test").surname("newPlayer3").build()
        );

        final Set<Player> addedPlayers = this.playerService.addMultiplePlayers(playersToAdd);
        final Set<Player> allPlayers = this.playerService.getAllPlayers();

        assertAll(
          () -> assertNotNull(addedPlayers, "Players set should not be null"),
          () -> assertFalse(addedPlayers.isEmpty(), "Players set should not be empty"),
          () -> assertTrue(allPlayers.containsAll(addedPlayers))
        );
    }

    @Test
    @Order(11)
    @DisplayName("GIVEN multiple players are added but one already exists, WHEN playerService.addMultiplePlayers is called, THEN it should throw PlayerAlreadyExistsException")
    void testAddMultiplePlayersButOneAlreadyExists() {
        final Set<Player> playersToAdd = Set.of(
          Player.builder().name("test").surname("player1").build(),
          Player.builder().name("test").surname("newPlayer2").build(),
          Player.builder().name("test").surname("newPlayer3").build()
        );

        assertThrows(PlayerAlreadyExistsException.class, () -> this.playerService.addMultiplePlayers(playersToAdd));
    }

}
