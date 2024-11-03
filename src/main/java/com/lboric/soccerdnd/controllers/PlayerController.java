package com.lboric.soccerdnd.controllers;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lboric.soccerdnd.dtos.PlayerDTO;
import com.lboric.soccerdnd.exceptions.PlayerNotFoundException;
import com.lboric.soccerdnd.models.Player;
import com.lboric.soccerdnd.services.PlayerService;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    PlayerController(final PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    ResponseEntity<SortedSet<PlayerDTO>> getPlayers() {
        final SortedSet<PlayerDTO> playerDTOs = this.playerService.getAllPlayers()
            .stream()
            .map(Player::toDTO)
            .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparingLong(PlayerDTO::id))));

        return ResponseEntity.ok(playerDTOs);
    }

    /**
     * Retrieves the name of a player based on their unique ID.
     *
     * @param id the unique identifier of the player
     * @return the name of the player as a String
     */
    @GetMapping("/{id}")
    ResponseEntity<PlayerDTO> getPlayerById(@PathVariable final Long id) {
        final PlayerDTO playerDTO = this.playerService.getPlayerById(id).toDTO();

        return ResponseEntity.ok(playerDTO);
    }

    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFound(final PlayerNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

}
