package com.lboric.soccerdnd.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PlayerControllerTest {

    private final MockMvc mockMvc;

    @Autowired
    PlayerControllerTest(final MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    @DisplayName("GIVEN an existing player ID, WHEN a GET request is made to /api/players/{id}, THEN it should return the player")
    void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/api/players/1"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("{\"id\":1,\"name\":\"test\",\"surname\":\"player1\"}")));
    }

    @Test
    @DisplayName("GIVEN a non-existing player ID, WHEN a GET request is made to /api/players/{id}, THEN it should return an error message")
    void shouldReturnErrorMessage() throws Exception {
        final long nonExistingId = 4L;

        this.mockMvc.perform(get("/api/players/" + nonExistingId))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(content().string(containsString("Player not found with ID: " + nonExistingId)));
    }

}
