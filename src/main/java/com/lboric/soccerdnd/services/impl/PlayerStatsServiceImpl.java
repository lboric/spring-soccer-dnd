package com.lboric.soccerdnd.services.impl;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lboric.soccerdnd.dtos.PlayerStatsDTO;
import com.lboric.soccerdnd.entities.PlayerEntity;
import com.lboric.soccerdnd.entities.PlayerStatsEntity;
import com.lboric.soccerdnd.exceptions.PlayerNotFoundException;
import com.lboric.soccerdnd.exceptions.PlayerStatsAlreadyExistsException;
import com.lboric.soccerdnd.exceptions.PlayerStatsNotFoundException;
import com.lboric.soccerdnd.models.PlayerStats;
import com.lboric.soccerdnd.repositories.PlayerStatsRepository;
import com.lboric.soccerdnd.services.PlayerService;
import com.lboric.soccerdnd.services.PlayerStatsService;
import com.lboric.soccerdnd.utils.ValidationUtils;

import lombok.extern.slf4j.Slf4j;

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

    @Override
    public PlayerStats getPlayerStatsById(final Long id) throws PlayerNotFoundException {
        if (ValidationUtils.checkIfPlayerIsMissingID(id)) throw new PlayerNotFoundException("You must provide an existing player ID.");

        return this.playerStatsRepository.findPlayerStatsById(id).map(PlayerStatsDTO::toModel)
        .orElseThrow(() -> {
            final String message = String.format("No stats found for player with ID: %d", id);

            log.warn(message);

            return new PlayerStatsNotFoundException(message);
        });
    }

    @Override
    public Set<PlayerStats> getAllPlayersStats() {
        return this.playerStatsRepository.findAllPlayerStats()
            .map(playerStats -> playerStats.stream()
            .map(PlayerStatsDTO::toModel)
            .collect(Collectors.toSet()))
            .orElseGet(Collections::emptySet);
    }

    @Override
    public PlayerStats addPlayerStats(final PlayerStats playerStats) {
        if (ValidationUtils.checkIfPlayerStatsIsMissingPlayerNameOrSurname(playerStats)) throw new IllegalArgumentException("You must provide player name and surname.");

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

    @Override
    public PlayerStats updatePlayerStats(final PlayerStats playerStats) throws PlayerStatsNotFoundException {
        final Long playerId = playerStats.getPlayerId();

        if (ValidationUtils.checkIfPlayerIsMissingID(playerId)) throw new PlayerNotFoundException("You must provide an existing player ID.");

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

    @Override
    public void deletePlayerStatsByNameSurnameAndSeasonYear(final PlayerStats playerStats) {
        if (ValidationUtils.checkIfPlayerStatsIsMissingPlayerNameOrSurnameOrSeasonYear(playerStats)) throw new IllegalArgumentException("You must provide player name, surname and season year.");

        final String name = playerStats.getName();
        final String surname = playerStats.getSurname();
        final int seasonYear = playerStats.getSeasonYear();

        if (this.playerStatsRepository.findByPlayerAndSeasonYear(this.playerService.getPlayerByNameAndSurname(name, surname).toEntity(), seasonYear).isEmpty()) {
            final String message = String.format("No stats found for player '%s %s' for season %d", name, surname, seasonYear);

            log.warn(message);

            throw new PlayerStatsNotFoundException(message);
        }

        this.playerStatsRepository.deleteByNameAndSurnameAndSeasonYear(name, surname, seasonYear);

        log.info("Deleted stats for player '{}' {} in season {}", name, surname, seasonYear);
    }

}
