package com.lboric.soccerdnd.services.impl;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lboric.soccerdnd.dtos.PlayerStatsDTO;
import com.lboric.soccerdnd.entities.PlayerEntity;
import com.lboric.soccerdnd.entities.PlayerStatsEntity;
import com.lboric.soccerdnd.exceptions.PlayerAlreadyExistsException;
import com.lboric.soccerdnd.exceptions.PlayerNotFoundException;
import com.lboric.soccerdnd.exceptions.PlayerStatsAlreadyExistsException;
import com.lboric.soccerdnd.exceptions.PlayerStatsNotFoundException;
import com.lboric.soccerdnd.models.PlayerStats;
import com.lboric.soccerdnd.repositories.PlayerStatsRepository;
import com.lboric.soccerdnd.services.PlayerService;
import com.lboric.soccerdnd.services.PlayerStatsService;
import com.lboric.soccerdnd.utils.ValidationUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of the {@link PlayerStatsService} interface that handles operations related to PlayerStats entities.
 * This service provides methods for retrieving, adding, updating, and deleting player statistics.
 *
 * <p>
 * It interacts with the {@link PlayerStatsRepository} and {@link PlayerService} to perform database operations
 * and utilizes custom exceptions to handle specific error scenarios.
 * </p>
 *
 * <p>
 * Supported operations:
 * <ul>
 *   <li>Retrieve statistics for a single player by their ID.</li>
 *   <li>Retrieve statistics for all players.</li>
 *   <li>Add new statistics for a player.</li>
 *   <li>Update existing statistics for a player.</li>
 *   <li>Delete statistics for a player based on their name, surname, and season year.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Exceptions handled:
 * <ul>
 *   <li>{@link PlayerNotFoundException} - if the player does not exist.</li>
 *   <li>{@link PlayerStatsNotFoundException} - if the statistics for the player are not found.</li>
 *   <li>{@link PlayerStatsAlreadyExistsException} - if statistics for the player already exist.</li>
 *   <li>{@link IllegalArgumentException} - if the provided input is invalid.</li>
 * </ul>
 * </p>
 *
 * <p>
 * This class also includes logging to capture significant actions and errors, aiding in debugging and operational monitoring.
 * </p>
 */
@Slf4j
@Service
public class PlayerStatsServiceImpl implements PlayerStatsService {

    private final PlayerStatsRepository playerStatsRepository;

    private final PlayerService playerService;

    @Autowired
    PlayerStatsServiceImpl(final PlayerStatsRepository playerStatsRepository, final PlayerService playerService) {
        this.playerStatsRepository = playerStatsRepository;
        this.playerService = playerService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerStats getPlayerStatsById(final Long id) throws PlayerNotFoundException {
        if (ValidationUtils.checkIfPlayerIsMissingID(id)) throw new PlayerNotFoundException("Player ID is missing or invalid. Please provide a valid existing player ID.");

        return this.playerStatsRepository.findPlayerStatsById(id).map(PlayerStatsDTO::toModel)
        .orElseThrow(() -> {
            final String message = String.format("No stats found for player with ID: %d", id);

            log.warn(message);

            return new PlayerStatsNotFoundException(message);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<PlayerStats> getAllPlayersStats() {
        return this.playerStatsRepository.findAllPlayerStats()
            .map(playerStats -> playerStats.stream()
            .map(PlayerStatsDTO::toModel)
            .collect(Collectors.toSet()))
            .orElseGet(Collections::emptySet);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerStats addPlayerStats(final PlayerStats playerStats) throws IllegalArgumentException, PlayerAlreadyExistsException {
        if (ValidationUtils.checkIfPlayerStatsIsMissingPlayerNameOrSurname(playerStats)) throw new IllegalArgumentException("Player ID is missing or invalid. Please provide a valid existing player ID.");

        final PlayerEntity playerEntity = this.playerService.getPlayerByNameAndSurname(playerStats.getName(), playerStats.getSurname()).toEntity();

        this.playerStatsRepository
            .findByPlayerAndSeasonYear(playerEntity, playerStats.getSeasonYear())
            .ifPresent(existingStats -> {
                final String message = String.format("Stats for player '%s %s' in season %d already exist.", playerStats.getName(), playerStats.getSurname(), playerStats.getSeasonYear());

                log.warn(message);

                throw new PlayerStatsAlreadyExistsException(message);
            });


        return this.playerStatsRepository.save(playerStats.toEntity(playerEntity)).toModel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerStats updatePlayerStats(final PlayerStats playerStats) throws PlayerStatsNotFoundException, PlayerNotFoundException {
        final Long playerId = playerStats.getPlayerId();

        if (ValidationUtils.checkIfPlayerIsMissingID(playerId)) throw new PlayerNotFoundException("Player ID is missing or invalid. Please provide a valid existing player ID.");

        final PlayerEntity playerEntity = this.playerService.getPlayerById(playerId).toEntity();
        final PlayerStatsEntity existingStats = this.playerStatsRepository
            .findByPlayerAndSeasonYear(playerEntity, playerStats.getSeasonYear())
            .orElseThrow(() -> {
               final String message = String.format("Stats not found for player with ID: %d and season year %d.", playerId, playerStats.getSeasonYear());

               log.warn(message);

               return new PlayerStatsNotFoundException(message);
           });

        existingStats.setNumberOfGoals(playerStats.getNumberOfGoals());

        return this.playerStatsRepository.save(existingStats).toModel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deletePlayerStatsByNameSurnameAndSeasonYear(final PlayerStats playerStats) throws IllegalArgumentException, PlayerNotFoundException {
        if (ValidationUtils.checkIfPlayerStatsIsMissingPlayerNameOrSurnameOrSeasonYear(playerStats)) throw new IllegalArgumentException("Player name, surname, or season year is missing or invalid. Please provide valid parameters.");


        final String name = playerStats.getName();
        final String surname = playerStats.getSurname();
        final int seasonYear = playerStats.getSeasonYear();

        if (this.playerStatsRepository.findByPlayerAndSeasonYear(this.playerService.getPlayerByNameAndSurname(name, surname).toEntity(), seasonYear).isEmpty()) {
            final String message = String.format("No stats found for player '%s %s' for season %d", name, surname, seasonYear);

            log.warn(message);

            throw new PlayerStatsNotFoundException(message);
        }

        this.playerStatsRepository.deleteByNameAndSurnameAndSeasonYear(name, surname, seasonYear);

        log.info("Successfully deleted stats for player '{}' {} in season {} from repository.", name, surname, seasonYear);
    }

}
