package com.lboric.soccerdnd.utils;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ControllerTestUtils {

    static Stream<Arguments> getPlayerByIdArgumentSetAndExpectedResultSet() {
        return Stream.of(
            Arguments.of(
               "1",
                "{\"id\":1,\"name\":\"test\",\"surname\":\"player1\"}"
            ),
            Arguments.of(
               "2",
                "{\"id\":2,\"name\":\"test\",\"surname\":\"player2\"}"
            ),
            Arguments.of(
               "3",
                "{\"id\":3,\"name\":\"test\",\"surname\":\"player3\"}"
            )
        );
    }

    static Stream<Arguments> getAllPlayersExpectedResultSet() {
        return Stream.of(
            Arguments.of("[{\"id\":1,\"name\":\"test\",\"surname\":\"player1\"},{\"id\":2,\"name\":\"test\",\"surname\":\"player2\"},{\"id\":3,\"name\":\"test\",\"surname\":\"player3\"}]")
        );
    }

    static Stream<Arguments> addPlayerArgumentSetAndExpectedResultSet() {
        return Stream.of(
            Arguments.of(
                "{\"name\":\"test\",\"surname\":\"player4\"}",
                "{\"id\":4,\"name\":\"test\",\"surname\":\"player4\"}"
            ),
            Arguments.of(
                "{\"name\":\"test\",\"surname\":\"player5\"}",
                "{\"id\":5,\"name\":\"test\",\"surname\":\"player5\"}"
            ),
            Arguments.of(
                "{\"name\":\"test\",\"surname\":\"player6\"}",
                "{\"id\":6,\"name\":\"test\",\"surname\":\"player6\"}"
            )
        );
    }

    static Stream<Arguments> addPlayerButItAlreadyExistsArgumentSetAndExpectedResultSet() {
        return Stream.of(
            Arguments.of(
                "{\"name\":\"test\",\"surname\":\"player1\"}",
                "Player with name: test and surname: player1 already exists."
            )
        );
    }

    static Stream<Arguments> updatePlayerArgumentSetAndExpectedResultSet() {
        return Stream.of(
            Arguments.of(
                "{\"id\":1,\"name\":\"newTest\",\"surname\":\"newPlayer1\"}",
                "{\"id\":1,\"name\":\"newTest\",\"surname\":\"newPlayer1\"}"
            )
        );
    }

    static Stream<Arguments> updatePlayerWhichDoesntExistArgumentSetAndExpectedResultSet() {
        return Stream.of(
            Arguments.of(
                "{\"id\":4,\"name\":\"newTest\",\"surname\":\"newPlayer4\"}",
                "Player with id: 4, name: newTest, surname: newPlayer4 not found."
            )
        );
    }

    static Stream<Arguments> deletePlayerByIdArgumentSet() {
        return Stream.of(
            Arguments.of("1"),
            Arguments.of("2"),
            Arguments.of("3")
        );
    }

    static Stream<Arguments> getPlayerStatsByPlayerIdArgumentSetAndExpectedResultSet() {
        return Stream.of(
            Arguments.of(
                "1",
                "{\"playerId\":1,\"name\":\"test\",\"surname\":\"player1\",\"seasonYear\":2024,\"numberOfGoals\":17}"
            ),
            Arguments.of(
                "2",
                "{\"playerId\":2,\"name\":\"test\",\"surname\":\"player2\",\"seasonYear\":2024,\"numberOfGoals\":21}"
            ),
            Arguments.of(
                "3",
                "{\"playerId\":3,\"name\":\"test\",\"surname\":\"player3\",\"seasonYear\":2024,\"numberOfGoals\":32}"
            )
        );
    }

    static Stream<Arguments> getAllPlayersStatsExpectedResultSet() {
        return Stream.of(
          Arguments.of("[{\"playerId\":1,\"name\":\"test\",\"surname\":\"player1\",\"seasonYear\":2024,\"numberOfGoals\":17},{\"playerId\":2,\"name\":\"test\",\"surname\":\"player2\",\"seasonYear\":2024,\"numberOfGoals\":21},{\"playerId\":3,\"name\":\"test\",\"surname\":\"player3\",\"seasonYear\":2024,\"numberOfGoals\":32}]")
        );
    }

    static Stream<Arguments> addPlayerStatsArgumentSetAndExpectedResultSet() {
        return Stream.of(
            Arguments.of(
                "{\"name\": \"test\", \"surname\": \"player1\", \"seasonYear\": \"2025\", \"numberOfGoals\": \"34\"}",
                "{\"playerId\":1,\"name\":\"test\",\"surname\":\"player1\",\"seasonYear\":2025,\"numberOfGoals\":34}"
            ),
            Arguments.of(
                "{\"name\": \"test\", \"surname\": \"player2\", \"seasonYear\": \"2026\", \"numberOfGoals\": \"31\"}",
                "{\"playerId\":2,\"name\":\"test\",\"surname\":\"player2\",\"seasonYear\":2026,\"numberOfGoals\":31}"
            ),
            Arguments.of(
                "{\"name\": \"test\", \"surname\": \"player3\", \"seasonYear\": \"2025\", \"numberOfGoals\": \"39\"}",
                "{\"playerId\":3,\"name\":\"test\",\"surname\":\"player3\",\"seasonYear\":2025,\"numberOfGoals\":39}"
            )
        );
    }

    static Stream<Arguments> addPlayerStatsForExistingSeasonYearArgumentSetAndExpectedResultSet() {
        return Stream.of(
          Arguments.of(
            "{\"name\": \"test\", \"surname\": \"player1\", \"seasonYear\": \"2024\", \"numberOfGoals\": \"34\"}",
            "Stats for player 'test player1' in season 2024 already exist."
          )
        );
    }

    static Stream<Arguments> addPlayerStatsMissingNameArgumentSetAndExpectedResultSet() {
        return Stream.of(
          Arguments.of(
            "{\"surname\": \"player1\", \"seasonYear\": \"2024\", \"numberOfGoals\": \"34\"}",
            "Bad request - Argument mismatch or invalid arguments"
          )
        );
    }

    static Stream<Arguments> updatePlayerStatsForExistingSeasonYearArgumentSetAndExpectedResultSet() {
        return Stream.of(
            Arguments.of(
                "{\"playerId\": \"1\", \"seasonYear\": \"2024\", \"numberOfGoals\": \"55\"}",
                "{\"playerId\":1,\"name\":\"test\",\"surname\":\"player1\",\"seasonYear\":2024,\"numberOfGoals\":55}"
            ),
            Arguments.of(
                "{\"playerId\": \"2\", \"seasonYear\": \"2024\", \"numberOfGoals\": \"45\"}",
                "{\"playerId\":2,\"name\":\"test\",\"surname\":\"player2\",\"seasonYear\":2024,\"numberOfGoals\":45}"
            ),
            Arguments.of(
                "{\"playerId\": \"3\", \"seasonYear\": \"2024\", \"numberOfGoals\": \"35\"}",
                "{\"playerId\":3,\"name\":\"test\",\"surname\":\"player3\",\"seasonYear\":2024,\"numberOfGoals\":35}"
            )
        );
    }

    static Stream<Arguments> updateNonExistentPlayerStatsArgumentSetAndExpectedResultSet() {
        return Stream.of(
        Arguments.of(
            "{\"playerId\": \"4\", \"seasonYear\": \"2024\", \"numberOfGoals\": \"55\"}",
            "Player with id: 4 not found."
        )
        );
    }

    static Stream<Arguments> deletePlayerStatsByNameAndSurnameAndSeasonYearArgumentSetAndExpectedResultSet() {
        return Stream.of(
            Arguments.of(
                "{\"name\": \"test\", \"surname\": \"player1\", \"seasonYear\": \"2024\"}"
            ),
            Arguments.of(
                "{\"name\": \"test\", \"surname\": \"player2\", \"seasonYear\": \"2024\"}"
            ),
            Arguments.of(
                "{\"name\": \"test\", \"surname\": \"player3\", \"seasonYear\": \"2024\"}"
            )
        );
    }

    static Stream<Arguments> deletePlayerStatsByNonExistingNameAndSurnameAndSeasonYearArgumentSetAndExpectedResultSet() {
        return Stream.of(
          Arguments.of(
            "{\"name\": \"test\", \"surname\": \"player4\", \"seasonYear\": \"2024\"}"
          )
        );
    }

}
