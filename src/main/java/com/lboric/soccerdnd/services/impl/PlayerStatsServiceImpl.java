package com.lboric.soccerdnd.services.impl;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lboric.soccerdnd.dtos.PlayerStatsDTO;
import com.lboric.soccerdnd.models.PlayerStats;
import com.lboric.soccerdnd.repositories.PlayerStatsRepository;
import com.lboric.soccerdnd.services.PlayerStatsService;

@Service
public class PlayerStatsServiceImpl implements PlayerStatsService {

    private final PlayerStatsRepository playerStatsRepository;

    @Autowired
    PlayerStatsServiceImpl(final PlayerStatsRepository playerStatsRepository) {
        this.playerStatsRepository = playerStatsRepository;
    }

    @Override
    public Set<PlayerStats> getAllPlayersStats() {
        return this.playerStatsRepository.findAllPlayerStats().stream().map(PlayerStatsDTO::toModel).collect(Collectors.toSet());
    }

}
