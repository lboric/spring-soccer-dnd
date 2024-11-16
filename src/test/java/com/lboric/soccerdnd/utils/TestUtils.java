package com.lboric.soccerdnd.utils;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import com.lboric.soccerdnd.models.Player;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestUtils {

    static Stream<Arguments> provideExistingPlayersIds() {
        return Stream.of(
          Arguments.of(1L),
          Arguments.of(2L),
          Arguments.of(3L)
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

}
