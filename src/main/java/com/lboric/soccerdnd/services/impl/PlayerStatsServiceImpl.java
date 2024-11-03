package com.lboric.soccerdnd.services.impl;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lboric.soccerdnd.dtos.PlayerStatsDTO;
import com.lboric.soccerdnd.exceptions.PlayerNotFoundException;
import com.lboric.soccerdnd.models.PlayerStats;
import com.lboric.soccerdnd.repositories.PlayerStatsRepository;
import com.lboric.soccerdnd.services.PlayerStatsService;
import com.lboric.soccerdnd.utils.ValidationUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PlayerStatsServiceImpl implements PlayerStatsService {

    private final PlayerStatsRepository playerStatsRepository;

    @Autowired
    PlayerStatsServiceImpl(final PlayerStatsRepository playerStatsRepository) {
        this.playerStatsRepository = playerStatsRepository;
    }

    @Override
    public PlayerStats getPlayerStatsById(final Long id) throws PlayerNotFoundException {
        if (ValidationUtils.checkIfPlayerIsMissingID(id)) throw new PlayerNotFoundException("You must provide an existing player ID.");

        return this.playerStatsRepository.findPlayerStatsById(id).map(PlayerStatsDTO::toModel)
        .orElseThrow(() -> {
            final String message = String.format("No stats found for player with id: %d", id);

            log.warn(message);

            return new PlayerNotFoundException(message);
        });
    }

    @Override
    public Set<PlayerStats> getAllPlayersStats() {
        return this.playerStatsRepository.findAllPlayerStats().stream().map(PlayerStatsDTO::toModel).collect(Collectors.toSet());
    }

    @Override
    public PlayerStats addPlayerStats() {
        return null;
    }

    @Override
    public PlayerStats updatePlayerStats() {
        return null;
    }

    @Override
    public void deletePlayerStatsByPlayerId() {

    }

    @Override
    public void deletePlayerStatsBySeasonYear() {

    }

}
