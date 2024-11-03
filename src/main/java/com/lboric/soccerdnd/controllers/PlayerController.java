package com.lboric.soccerdnd.controllers;

import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lboric.soccerdnd.dtos.PlayerDTO;
import com.lboric.soccerdnd.models.Player;
import com.lboric.soccerdnd.services.PlayerService;

import lombok.NonNull;

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

    @GetMapping("/{id}")
    ResponseEntity<PlayerDTO> getPlayerById(@NonNull @PathVariable final Long id) {
        final PlayerDTO playerDTO = this.playerService.getPlayerById(id).toDTO();

        return ResponseEntity.ok(playerDTO);
    }

    @PostMapping("/add-player")
    ResponseEntity<PlayerDTO> addPlayer(@RequestBody final PlayerDTO playerDTO) {
        final PlayerDTO resultPlayerDTO = this.playerService.addPlayer(playerDTO.toModel()).toDTO();

        return ResponseEntity.ok(resultPlayerDTO);
    }

    @PostMapping("/add-players")
    ResponseEntity<PlayerDTO> addPlayer(@RequestBody final List<PlayerDTO> playerDTO) {
        return null;
    }

    @PutMapping("/update-player")
    ResponseEntity<PlayerDTO> updatePlayer(@RequestBody final PlayerDTO playerDTO) {
        final PlayerDTO resultPlayerDTO = this.playerService.updatePlayer(playerDTO.toModel()).toDTO();

        return ResponseEntity.ok(resultPlayerDTO);
    }

    @DeleteMapping("/delete-player/{id}")
    void deletePlayer(@NonNull @PathVariable final Long id) {
        this.playerService.deletePlayerById(id);
    }

}
