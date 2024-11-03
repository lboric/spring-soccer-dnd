package com.lboric.soccerdnd.unit.services;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lboric.soccerdnd.exceptions.PlayerNotFoundException;
import com.lboric.soccerdnd.models.Player;
import com.lboric.soccerdnd.services.PlayerService;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class PlayerServiceImplTest {

    private final PlayerService playerService;

    @Autowired
    PlayerServiceImplTest(final PlayerService playerService) {
        this.playerService = playerService;
    }

    @ParameterizedTest
    @MethodSource("providePlayerIds")
    @DisplayName("GIVEN an existing player ID, WHEN playerService.getPlayerById is called, THEN it should return the correct player")
    void testGetPlayer(final long id, final String expectedName, final String expectedSurname) {
        final Player player = this.playerService.getPlayerById(id);

        assertAll(
          () -> assertNotNull(player, "Player object should not be null"),
          () -> assertEquals(id, player.getId(), "Player ID should match"),
          () -> assertEquals(expectedName, player.getName(), "Player name should match"),
          () -> assertEquals(expectedSurname, player.getSurname(), "Player surname should match")
        );
    }

    @Test
    @DisplayName("GIVEN a non-existent player ID, WHEN playerService.getPlayerById is called, THEN it should throw PlayerNotFoundException")
    void testGetNonExistentPlayer() {
        final long nonExistingPlayerId = 4L;

        assertThrows(PlayerNotFoundException.class, () -> this.playerService.getPlayerById(nonExistingPlayerId));
    }

    private static Stream<Arguments> providePlayerIds() {
        return Stream.of(
          Arguments.of(1L, "test", "player1"),
          Arguments.of(2L, "test", "player2"),
          Arguments.of(3L, "test", "player3")
        );
    }

}
