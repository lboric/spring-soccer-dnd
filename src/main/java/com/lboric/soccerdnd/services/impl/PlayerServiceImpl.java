package com.lboric.soccerdnd.services.impl;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lboric.soccerdnd.entities.PlayerEntity;
import com.lboric.soccerdnd.exceptions.PlayerNotFoundException;
import com.lboric.soccerdnd.models.Player;
import com.lboric.soccerdnd.repositories.PlayerRepository;
import com.lboric.soccerdnd.services.PlayerService;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    PlayerServiceImpl(final PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getPlayerById(final long id) throws PlayerNotFoundException {
        return this.playerRepository.findById(id)
            .map(PlayerEntity::toModel)
            .orElseThrow(() -> new PlayerNotFoundException("Player not found with ID: " + id));
    }

    @Override
    public Set<Player> getAllPlayers() {
        return StreamSupport.stream(this.playerRepository.findAll().spliterator(), false)
            .map(PlayerEntity::toModel)
            .collect(Collectors.toSet());
    }

}
