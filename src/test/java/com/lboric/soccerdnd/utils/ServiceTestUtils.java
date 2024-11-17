package com.lboric.soccerdnd.utils;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import com.lboric.soccerdnd.models.Player;
import com.lboric.soccerdnd.models.PlayerStats;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ServiceTestUtils {

    static Stream<Arguments> provideExistingPlayersIds() {
        return Stream.of(
          Arguments.of(1L),
          Arguments.of(2L),
          Arguments.of(3L)
        );
    }

    static Stream<Arguments> provideExistingPlayersNameAndSurname() {
        return Stream.of(
          Arguments.of("test", "player1"),
          Arguments.of("test", "player2"),
          Arguments.of("test", "player3")
        );
    }

    static Stream<Arguments> provideExistingPlayersIdAndNameAndSurname() {
        return Stream.of(
          Arguments.of(1L, "test", "player1"),
          Arguments.of(2L, "test", "player2"),
          Arguments.of(3L, "test", "player3")
        );
    }

    static Stream<Arguments> providePlayersWithoutIds() {
        return Stream.of(
          Arguments.of(Player.builder().name("test").surname("player4").build()),
          Arguments.of(Player.builder().name("test").surname("player5").build()),
          Arguments.of(Player.builder().name("test").surname("player6").build())
        );
    }

    static Stream<Arguments> provideUpdatedPlayersWithExistingIds() {
        return Stream.of(
          Arguments.of(Player.builder().id(1L).name("updatedTest").surname("updatedPlayer1").build()),
          Arguments.of(Player.builder().id(2L).name("updatedTest").surname("updatedPlayer2").build()),
          Arguments.of(Player.builder().id(3L).name("updatedTest").surname("updatedPlayer3").build())
        );
    }

    static Stream<Arguments> provideExistingPlayersStats() {
        return Stream.of(
          Arguments.of(1L, "test", "player1", "2024", "17"),
          Arguments.of(2L, "test", "player2", "2024", "21"),
          Arguments.of(3L, "test", "player3", "2024", "32")
        );
    }

    static Stream<Arguments> providePlayersStatsToAdd() {
        return Stream.of(
          Arguments.of(PlayerStats.builder().playerId(1L).name("test").surname("player1").seasonYear(2025).numberOfGoals(1).build()),
          Arguments.of(PlayerStats.builder().playerId(2L).name("test").surname("player2").seasonYear(2025).numberOfGoals(2).build()),
          Arguments.of(PlayerStats.builder().playerId(3L).name("test").surname("player3").seasonYear(2025).numberOfGoals(3).build())
        );
    }

    static Stream<Arguments> provideUpdatedPlayersStats() {
        return Stream.of(
          Arguments.of(PlayerStats.builder().playerId(1L).name("test").surname("player1").seasonYear(2024).numberOfGoals(1).build()),
          Arguments.of(PlayerStats.builder().playerId(2L).name("test").surname("player2").seasonYear(2024).numberOfGoals(2).build()),
          Arguments.of(PlayerStats.builder().playerId(3L).name("test").surname("player3").seasonYear(2024).numberOfGoals(3).build())
        );
    }

    static Stream<Arguments> providePlayersStatsToDelete() {
        return Stream.of(
          Arguments.of(PlayerStats.builder().playerId(1L).name("test").surname("player1").seasonYear(2024).numberOfGoals(17).build()),
          Arguments.of(PlayerStats.builder().playerId(2L).name("test").surname("player2").seasonYear(2024).numberOfGoals(21).build()),
          Arguments.of(PlayerStats.builder().playerId(3L).name("test").surname("player3").seasonYear(2024).numberOfGoals(32).build())
        );
    }

}
