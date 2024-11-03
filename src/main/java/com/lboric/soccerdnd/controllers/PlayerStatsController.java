package com.lboric.soccerdnd.controllers;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lboric.soccerdnd.dtos.PlayerStatsDTO;
import com.lboric.soccerdnd.models.PlayerStats;
import com.lboric.soccerdnd.services.PlayerStatsService;

@RestController
@RequestMapping("/api/player-stats")
public class PlayerStatsController {

    private final PlayerStatsService playerStatsService;

    @Autowired
    PlayerStatsController(final PlayerStatsService playerStatsService) {
        this.playerStatsService = playerStatsService;
    }

    @GetMapping
    ResponseEntity<SortedSet<PlayerStatsDTO>> getPlayers() {
        final SortedSet<PlayerStatsDTO> playerStatsDTO = this.playerStatsService.getAllPlayersStats()
        .stream()
        .map(PlayerStats::toDTO)
        .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparingLong(PlayerStatsDTO::id))));

        return ResponseEntity.ok(playerStatsDTO);
    }

}
