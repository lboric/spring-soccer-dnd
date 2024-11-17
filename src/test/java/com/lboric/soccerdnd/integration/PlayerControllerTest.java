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
class PlayerControllerTest {

    private final MockMvc mockMvc;

    @Autowired
    PlayerControllerTest(final MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @ParameterizedTest
    @Order(1)
    @MethodSource("com.lboric.soccerdnd.utils.ControllerTestUtils#getPlayerByIdArgumentSetAndExpectedResultSet")
    @DisplayName("GIVEN an existing player ID, WHEN a GET request is made to /api/players/{id}, THEN it should return the player")
    void testGetPlayerById(final String id, final String expectedResponse) throws Exception {
        this.mockMvc.perform(get("/api/players/" + id))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(expectedResponse)));
    }

    @Test
    @Order(2)
    @DisplayName("GIVEN a non-existing player ID, WHEN a GET request is made to /api/players/{id}, THEN it should return an error message")
    void testGetPlayerByNonExistentId() throws Exception {
        this.mockMvc.perform(get("/api/players/4"))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(content().string(containsString("Player with id: 4 not found.")));
    }

    @ParameterizedTest
    @Order(3)
    @MethodSource("com.lboric.soccerdnd.utils.ControllerTestUtils#getAllPlayersExpectedResultSet")
    @DisplayName("GIVEN a list of player, WHEN a GET request is made to /api/players, THEN it should list of recorded players")
    void testGetAllPlayers(final String expectedResponse) throws Exception {
        this.mockMvc.perform(get("/api/players"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(expectedResponse)));
    }

    @ParameterizedTest
    @Order(4)
    @Rollback
    @Transactional
    @MethodSource("com.lboric.soccerdnd.utils.ControllerTestUtils#addPlayerArgumentSetAndExpectedResultSet")
    @DisplayName("GIVEN an new player, WHEN a POST request is made to /api/players/add-player, THEN it should return the added player")
    void testAddPlayer(final String requestBody, final String expectedResponse) throws Exception {
        this.mockMvc.perform(post("/api/players/add-player")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(expectedResponse)));
    }

    @ParameterizedTest
    @Order(5)
    @MethodSource("com.lboric.soccerdnd.utils.ControllerTestUtils#addPlayerButItAlreadyExistsArgumentSetAndExpectedResultSet")
    @DisplayName("GIVEN an existing player, WHEN a POST request is made to /api/players/add-player, THEN it should return an error message")
    void testAddPlayerButItAlreadyExists(final String requestBody, final String expectedResponse) throws Exception {
        this.mockMvc.perform(post("/api/players/add-player")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andDo(print())
            .andExpect(status().isConflict())
            .andExpect(content().string(containsString(expectedResponse)));
    }

    @ParameterizedTest
    @Order(6)
    @MethodSource("com.lboric.soccerdnd.utils.ControllerTestUtils#updatePlayerArgumentSetAndExpectedResultSet")
    @DisplayName("GIVEN an existing player, WHEN a PUT request is made to /api/players/update-player, THEN it should return the updated player")
    void testUpdatePlayer(final String requestBody, final String expectedResponse) throws Exception {
        this.mockMvc.perform(put("/api/players/update-player")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(expectedResponse)));
    }

    @ParameterizedTest
    @Order(7)
    @MethodSource("com.lboric.soccerdnd.utils.ControllerTestUtils#updatePlayerWhichDoesntExistArgumentSetAndExpectedResultSet")
    @DisplayName("GIVEN an non-existing player, WHEN a PUT request is made to /api/players/update-player, THEN it should return an error message")
    void testUpdatePlayerWhichDoesntExist(final String requestBody, final String expectedResponse) throws Exception {
        this.mockMvc.perform(put("/api/players/update-player")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(content().string(containsString(expectedResponse)));
    }

    @ParameterizedTest
    @Order(8)
    @MethodSource("com.lboric.soccerdnd.utils.ControllerTestUtils#deletePlayerByIdArgumentSet")
    @DisplayName("GIVEN an existing player, WHEN a DELETE request is made to /api/players/delete-player, THEN it should delete the player")
    void testDeletePlayer(final String id) throws Exception {
        this.mockMvc.perform(delete("/api/players/delete-player/" + id))
            .andDo(print())
            .andExpect(status().isOk());
    }

}
