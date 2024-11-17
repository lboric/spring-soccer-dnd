package com.lboric.soccerdnd.controllers;

import java.util.Comparator;
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

import com.lboric.soccerdnd.dtos.PlayerStatsDTO;
import com.lboric.soccerdnd.models.PlayerStats;
import com.lboric.soccerdnd.services.PlayerStatsService;

import lombok.NonNull;

@RestController
@RequestMapping("/api/player-stats")
public class PlayerStatsController {

    private final PlayerStatsService playerStatsService;

    @Autowired
    PlayerStatsController(final PlayerStatsService playerStatsService) {
        this.playerStatsService = playerStatsService;
    }

    @GetMapping
    ResponseEntity<SortedSet<PlayerStatsDTO>> getPlayersStats() {
        final SortedSet<PlayerStatsDTO> playerStatsDTO = this.playerStatsService.getAllPlayersStats()
        .stream()
        .map(PlayerStats::toDTO)
        .collect(Collectors.toCollection(
          () -> new TreeSet<>(Comparator.comparingLong(PlayerStatsDTO::playerId).thenComparingInt(PlayerStatsDTO::seasonYear)))
        );

        return ResponseEntity.ok(playerStatsDTO);
    }

    @GetMapping("/{id}")
    ResponseEntity<PlayerStatsDTO> getPlayerStatsByPlayerId(@NonNull @PathVariable final Long id) {
        final PlayerStatsDTO playerStatsDTO = this.playerStatsService.getPlayerStatsById(id).toDTO();

        return ResponseEntity.ok(playerStatsDTO);
    }

    @PostMapping("/add-player-stats")
    ResponseEntity<PlayerStatsDTO> addPlayerStats(@NonNull @RequestBody final PlayerStatsDTO playerStatsDTO) {
        final PlayerStatsDTO resultPlayerDTO = this.playerStatsService.addPlayerStats(playerStatsDTO.toModel()).toDTO();

        return ResponseEntity.ok(resultPlayerDTO);
    }

    @PutMapping("/update-player-stats")
    ResponseEntity<PlayerStatsDTO> updatePlayerStats(@NonNull @RequestBody final PlayerStatsDTO playerStatsDTO) {
        final PlayerStatsDTO resultPlayerDTO = this.playerStatsService.updatePlayerStats(playerStatsDTO.toModel()).toDTO();

        return ResponseEntity.ok(resultPlayerDTO);
    }

    @DeleteMapping("/delete-player-stats")
    void deletePlayer(@RequestBody final PlayerStatsDTO playerStatsDTO) {
        this.playerStatsService.deletePlayerStatsByNameSurnameAndSeasonYear(playerStatsDTO.toModel());
    }

}
