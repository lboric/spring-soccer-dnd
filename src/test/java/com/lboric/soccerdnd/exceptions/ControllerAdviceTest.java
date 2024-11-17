package com.lboric.soccerdnd.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.lboric.soccerdnd.services.PlayerService;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ControllerAdviceTest {

    private final MockMvc mockMvc;

    @MockBean
    private PlayerService playerService;

    @Autowired
    ControllerAdviceTest(final MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    @DisplayName("GIVEN an API call to non-existing endpoint, WHEN a GET request is made to /api/non-existing, THEN it should return an error message")
    void testCallNonExistingAPIEndpoint() throws Exception {
        this.mockMvc.perform(get("/api/non-existing"))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(content().string(containsString("API endpoint doesn't exist.")));
    }

    @Test
    @DisplayName("GIVEN an API call to an existing endpoint, WHEN a GET request is made to API, if something goes wrong THEN it should return an error message")
    void testInternalServerError() throws Exception {
        Mockito.when(this.playerService.getAllPlayers()).thenThrow(new RuntimeException("Simulated error"));

        this.mockMvc.perform(get("/api/players"))
            .andDo(print())
            .andExpect(status().is5xxServerError())
            .andExpect(content().string(containsString("An unexpected error occurred.")));
    }

}
