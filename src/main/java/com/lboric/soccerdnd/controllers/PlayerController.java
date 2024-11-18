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

import com.lboric.soccerdnd.dtos.PlayerDTO;
import com.lboric.soccerdnd.models.Player;
import com.lboric.soccerdnd.services.PlayerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.Valid;
import lombok.NonNull;

/**
 * REST controller for managing player entities.
 *
 * <p>
 * This controller provides endpoints for CRUD operations on players,
 * including retrieval, addition, updating, and deletion.
 * </p>
 *
 * <p>
 * Base URL: {@code /api/players}
 * </p>
 */
@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerService playerService;

    /**
     * Constructs a new {@link PlayerController} with the specified service.
     *
     * @param playerService the service used for player operations
     */
    @Autowired
    PlayerController(final PlayerService playerService) {
        this.playerService = playerService;
    }

    /**
     * Retrieves all players, sorted by their ID.
     *
     * @return a {@link ResponseEntity} containing a sorted set of {@link PlayerDTO}
     */
    @Operation(summary = "Retrieve all players", description = "Fetches all players, sorted by their IDs.")
    @ApiResponse(
      responseCode = "200",
      description = "Players retrieved successfully",
      content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerDTO.class))
    )
    @GetMapping
    ResponseEntity<SortedSet<PlayerDTO>> getPlayers() {
        final SortedSet<PlayerDTO> playerDTOs = this.playerService.getAllPlayers()
            .stream()
            .map(Player::toDTO)
            .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparingLong(PlayerDTO::id))));

        return ResponseEntity.ok(playerDTOs);
    }

    /**
     * Retrieves a player by their unique ID.
     *
     * @param id the ID of the player to retrieve
     * @return a {@link ResponseEntity} containing the {@link PlayerDTO} of the specified player
     */
    @Operation(summary = "Retrieve player by ID", description = "Fetches a specific player using their ID.")
    @ApiResponses(value = {
      @ApiResponse(
        responseCode = "200",
        description = "Player retrieved successfully",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerDTO.class))
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Player not found"
      )
    })
    @GetMapping("/{id}")
    ResponseEntity<PlayerDTO> getPlayerById(@PathVariable final Long id) {
        final PlayerDTO playerDTO = this.playerService.getPlayerById(id).toDTO();

        return ResponseEntity.ok(playerDTO);
    }

    /**
     * Adds a new player to the system.
     *
     * @param playerDTO the {@link PlayerDTO} representing the player to be added
     * @return a {@link ResponseEntity} containing the created {@link PlayerDTO}
     */
    @Operation(summary = "Add a new player", description = "Creates a new player.")
    @ApiResponse(
      responseCode = "201",
      description = "Player created successfully",
      content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerDTO.class))
    )
    @PostMapping("/add")
    ResponseEntity<PlayerDTO> addPlayer(@NonNull @Valid @RequestBody final PlayerDTO playerDTO) {
        final PlayerDTO resultPlayerDTO = this.playerService.addPlayer(playerDTO.toModel()).toDTO();

        return ResponseEntity.status(HttpStatus.CREATED).body(resultPlayerDTO);
    }

    /**
     * Updates an existing player.
     *
     * @param playerDTO the {@link PlayerDTO} containing the updated player information
     * @return a {@link ResponseEntity} containing the updated {@link PlayerDTO}
     */
    @Operation(summary = "Update a player", description = "Updates an existing player's information.")
    @ApiResponse(
      responseCode = "200",
      description = "Player updated successfully",
      content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerDTO.class))
    )
    @PutMapping("/update")
    ResponseEntity<PlayerDTO> updatePlayer(@NonNull @Valid @RequestBody final PlayerDTO playerDTO) {
        final PlayerDTO resultPlayerDTO = this.playerService.updatePlayer(playerDTO.toModel()).toDTO();

        return ResponseEntity.ok(resultPlayerDTO);
    }

    /**
     * Deletes a player by their unique ID.
     *
     * @param id the ID of the player to delete
     * @return a {@link ResponseEntity} with no content upon successful deletion
     */
    @Operation(summary = "Delete a player", description = "Deletes a player using their ID.")
    @ApiResponse(
      responseCode = "204",
      description = "Player deleted successfully"
    )
    @DeleteMapping("/delete/{id}")
    ResponseEntity<Void> deletePlayer(@PathVariable final Long id) {
        this.playerService.deletePlayerById(id);

        return ResponseEntity.noContent().build();
    }

}
