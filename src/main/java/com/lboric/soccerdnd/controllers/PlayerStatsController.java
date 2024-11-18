package com.lboric.soccerdnd.controllers;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.Valid;
import lombok.NonNull;

/**
 * REST controller for managing player statistics.
 *
 * <p>
 * This controller provides endpoints for CRUD operations on player statistics,
 * including retrieval, addition, updating, and deletion.
 * </p>
 *
 * <p>
 * Base URL: {@code /api/player-stats}
 * </p>
 */
@RestController
@RequestMapping("/api/player-stats")
public class PlayerStatsController {

    private final PlayerStatsService playerStatsService;

    /**
     * Constructs a new {@link PlayerStatsController} with the specified service.
     *
     * @param playerStatsService the service used for player statistics operations
     */
    @Autowired
    PlayerStatsController(final PlayerStatsService playerStatsService) {
        this.playerStatsService = playerStatsService;
    }

    /**
     * Retrieves the statistics for all players, sorted by player ID and season year.
     *
     * @return a {@link ResponseEntity} containing a sorted set of {@link PlayerStatsDTO}
     */
    @Operation(summary = "Retrieve all player statistics", description = "Fetches statistics for all players, sorted by player ID and season year.")
    @ApiResponse(
        responseCode = "200",
        description = "Player statistics retrieved successfully",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerStatsDTO.class))
    )
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

    /**
     * Retrieves the statistics for a specific player by their ID.
     *
     * @param id the ID of the player whose statistics are to be retrieved
     * @return a {@link ResponseEntity} containing the {@link PlayerStatsDTO} of the specified player
     */
    @Operation(summary = "Retrieve player statistics by ID", description = "Fetches statistics for a specific player using their ID.")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Player statistics retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerStatsDTO.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Player not found"
        )
    })
    @GetMapping("/{id}")
    ResponseEntity<PlayerStatsDTO> getPlayerStatsByPlayerId(@PathVariable final Long id) {
        final PlayerStatsDTO playerStatsDTO = this.playerStatsService.getPlayerStatsById(id).toDTO();

        return ResponseEntity.ok(playerStatsDTO);
    }

    /**
     * Adds new statistics for a player.
     *
     * @param playerStatsDTO the {@link PlayerStatsDTO} representing the player's statistics to be added
     * @return a {@link ResponseEntity} containing the created {@link PlayerStatsDTO}
     */
    @Operation(summary = "Add new player statistics", description = "Creates new statistics for a player.")
    @ApiResponse(
        responseCode = "201",
        description = "Player statistics created successfully",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerStatsDTO.class))
    )
    @PostMapping("/add")
    ResponseEntity<PlayerStatsDTO> addPlayerStats(@NonNull @Valid @RequestBody final PlayerStatsDTO playerStatsDTO) {
        final PlayerStatsDTO resultPlayerDTO = this.playerStatsService.addPlayerStats(playerStatsDTO.toModel()).toDTO();

        return ResponseEntity.status(HttpStatus.CREATED).body(resultPlayerDTO);
    }

    /**
     * Updates existing statistics for a player.
     *
     * @param playerStatsDTO the {@link PlayerStatsDTO} containing the updated player statistics
     * @return a {@link ResponseEntity} containing the updated {@link PlayerStatsDTO}
     */
    @Operation(summary = "Update player statistics", description = "Updates existing statistics for a player.")
    @ApiResponse(
        responseCode = "200",
        description = "Player statistics updated successfully",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerStatsDTO.class))
    )
    @PutMapping("/update")
    ResponseEntity<PlayerStatsDTO> updatePlayerStats(@NonNull @Valid @RequestBody final PlayerStatsDTO playerStatsDTO) {
        final PlayerStatsDTO resultPlayerDTO = this.playerStatsService.updatePlayerStats(playerStatsDTO.toModel()).toDTO();

        return ResponseEntity.ok(resultPlayerDTO);
    }

    /**
     * Deletes statistics for a specific player based on their name, surname, and season year.
     *
     * @param playerStatsDTO the {@link PlayerStatsDTO} identifying the player's statistics to be deleted
     * @return a {@link ResponseEntity} with no content upon successful deletion
     */
    @Operation(summary = "Delete player statistics", description = "Deletes statistics for a player based on their name, surname, and season year.")
    @ApiResponse(
        responseCode = "204",
        description = "Player statistics deleted successfully"
    )
    @DeleteMapping("/delete")
    ResponseEntity<Void> deletePlayer(@NonNull @Valid @RequestBody final PlayerStatsDTO playerStatsDTO) {
        this.playerStatsService.deletePlayerStatsByNameSurnameAndSeasonYear(playerStatsDTO.toModel());

        return ResponseEntity.noContent().build();
    }

}
