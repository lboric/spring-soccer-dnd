package com.lboric.soccerdnd.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.transaction.Transactional;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PlayerStatsControllerTest {

    private final MockMvc mockMvc;

    @Autowired
    PlayerStatsControllerTest(final MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @ParameterizedTest
    @Order(1)
    @MethodSource("com.lboric.soccerdnd.utils.ControllerTestUtils#getPlayerStatsByPlayerIdArgumentSetAndExpectedResultSet")
    @DisplayName("GIVEN an existing player ID, WHEN a GET request is made to /api/player-stats/{id}, THEN it should return the player stats")
    void testGetPlayerStatsByPlayerId(final String id, final String expectedResponse) throws Exception {
        this.mockMvc.perform(get("/api/player-stats/" + id))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(expectedResponse)));
    }

    @Test
    @Order(2)
    @DisplayName("GIVEN a non-existing player ID, WHEN a GET request is made to /api/player-stats/{id}, THEN it should return an error message")
    void testGetPlayerByNonExistentId() throws Exception {
        this.mockMvc.perform(get("/api/player-stats/4"))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(content().string(containsString("No stats found for player with ID: 4")));
    }

    @ParameterizedTest
    @Order(3)
    @MethodSource("com.lboric.soccerdnd.utils.ControllerTestUtils#getAllPlayersStatsExpectedResultSet")
    @DisplayName("GIVEN a list of player stats, WHEN a GET request is made to /api/players-stats, THEN it should list of recorded player stats")
    void testGetAllPlayersStats(final String expectedResponse) throws Exception {
        this.mockMvc.perform(get("/api/player-stats"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(expectedResponse)));
    }

    @ParameterizedTest
    @Order(4)
    @Rollback
    @Transactional
    @MethodSource("com.lboric.soccerdnd.utils.ControllerTestUtils#addPlayerStatsArgumentSetAndExpectedResultSet")
    @DisplayName("GIVEN an new player stats, WHEN a POST request is made to /api/player-stats/add, THEN it should return the added player stats")
    void testAddPlayerStats(final String requestBody, final String expectedResponse) throws Exception {
        this.mockMvc.perform(post("/api/player-stats/add")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(content().string(containsString(expectedResponse)));
    }

    @ParameterizedTest
    @Order(5)
    @Rollback
    @Transactional
    @MethodSource("com.lboric.soccerdnd.utils.ControllerTestUtils#addPlayerStatsForExistingSeasonYearArgumentSetAndExpectedResultSet")
    @DisplayName("GIVEN an new player stats, WHEN a POST request is made to /api/player-stats/add, THEN it should return an error message")
    void testAddPlayerStatsForExistingSeasonYear(final String requestBody, final String expectedResponse) throws Exception {
        this.mockMvc.perform(post("/api/player-stats/add")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andDo(print())
            .andExpect(status().isConflict())
            .andExpect(content().string(containsString(expectedResponse)));
    }

    @ParameterizedTest
    @Order(6)
    @Rollback
    @Transactional
    @MethodSource("com.lboric.soccerdnd.utils.ControllerTestUtils#addPlayerStatsMissingNameArgumentSetAndExpectedResultSet")
    @DisplayName("GIVEN an new player stats but missing name, WHEN a POST request is made to /api/player-stats/add, THEN it should return an error message")
    void testAddPlayerStatsWithMissingArguments(final String requestBody, final String expectedResponse) throws Exception {
        this.mockMvc.perform(post("/api/player-stats/add")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andDo(print())
            .andExpect(status().is4xxClientError());
    }

    @ParameterizedTest
    @Order(7)
    @Rollback
    @Transactional
    @MethodSource("com.lboric.soccerdnd.utils.ControllerTestUtils#updatePlayerStatsForExistingSeasonYearArgumentSetAndExpectedResultSet")
    @DisplayName("GIVEN an existing player stats, WHEN a PUT request is made to /api/player-stats/update, THEN it should return the updated player stats")
    void testUpdatePlayerStats(final String requestBody, final String expectedResponse) throws Exception {
        this.mockMvc.perform(put("/api/player-stats/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(expectedResponse)));
    }

    @ParameterizedTest
    @Order(8)
    @MethodSource("com.lboric.soccerdnd.utils.ControllerTestUtils#updateNonExistentPlayerStatsArgumentSetAndExpectedResultSet")
    @DisplayName("GIVEN an non-existing player, WHEN a PUT request is made to /api/player-stats/update, THEN it should return an error message")
    void testUpdateNonExistentPlayerStats(final String requestBody, final String expectedResponse) throws Exception {
        this.mockMvc.perform(put("/api/player-stats/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(content().string(containsString(expectedResponse)));
    }

    @ParameterizedTest
    @Order(9)
    @MethodSource("com.lboric.soccerdnd.utils.ControllerTestUtils#updateNonExistentPlayerStatsArgumentSetAndExpectedResultSet")
    @DisplayName("GIVEN an non-existing player stats for season year, WHEN a PUT request is made to /api/player-stats/update, THEN it should return an error message")
    void testUpdateNonExistentPlayerStatsRecord(final String requestBody, final String expectedResponse) throws Exception {
        this.mockMvc.perform(put("/api/player-stats/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(content().string(containsString(expectedResponse)));
    }

    @ParameterizedTest
    @Order(10)
    @Rollback
    @Transactional
    @MethodSource("com.lboric.soccerdnd.utils.ControllerTestUtils#deletePlayerStatsByNameAndSurnameAndSeasonYearArgumentSetAndExpectedResultSet")
    @DisplayName("GIVEN an existing player stats, WHEN a DELETE request is made to /api/player-stats/delete, THEN it should delete the player stats")
    void testDeletePlayerStatsByNameAndSurname(final String requestBody) throws Exception {
        this.mockMvc.perform(delete("/api/player-stats/delete")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andDo(print())
            .andExpect(status().isNoContent());
    }

    @ParameterizedTest
    @Order(11)
    @MethodSource("com.lboric.soccerdnd.utils.ControllerTestUtils#deletePlayerStatsByNonExistingNameAndSurnameAndSeasonYearArgumentSetAndExpectedResultSet")
    @DisplayName("GIVEN an non-existing player stats, WHEN a DELETE request is made to /api/player-stats/delete, THEN it should return an error message")
    void testDeletePlayerStatsByNonExistingNameAndSurname(final String requestBody) throws Exception {
        this.mockMvc.perform(delete("/api/player-stats/delete")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andDo(print())
            .andExpect(status().isNotFound());
    }

}
