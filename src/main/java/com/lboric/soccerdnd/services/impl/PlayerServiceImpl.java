package com.lboric.soccerdnd.services.impl;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.lboric.soccerdnd.entities.PlayerEntity;
import com.lboric.soccerdnd.exceptions.PlayerAlreadyExistsException;
import com.lboric.soccerdnd.exceptions.PlayerNotFoundException;
import com.lboric.soccerdnd.models.Player;
import com.lboric.soccerdnd.repositories.PlayerRepository;
import com.lboric.soccerdnd.services.PlayerService;
import com.lboric.soccerdnd.utils.PlayerUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
    public Player getPlayerById(final Long id) throws PlayerNotFoundException {
        if (PlayerUtils.checkIfPlayerIsMissingID(id)) throw new PlayerNotFoundException("You must provide existing player ID.");

        return this.playerRepository.findById(id)
            .map(PlayerEntity::toModel)
            .orElseThrow(() -> new PlayerNotFoundException(String.format("Player with ID: %d not found.", id)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Player> getAllPlayers() {
        return PlayerUtils.convertEntitiesToModels(this.playerRepository.findAll());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player addPlayer(final Player player) throws PlayerAlreadyExistsException{
        try {
            return this.playerRepository.save(player.toEntity()).toModel();
        } catch (final DataIntegrityViolationException e) {
            log.error("Player {} {} already exists", player.getName(), player.getSurname(), e);

            throw new PlayerAlreadyExistsException(String.format("Player with NAME: %s and SURNAME: %s already exists.", player.getName(), player.getSurname()));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Player> addMultiplePlayers(final Set<Player> players) throws PlayerAlreadyExistsException {
        try {
            return PlayerUtils.convertEntitiesToModels(this.playerRepository.saveAll(
              players.stream().map(Player::toEntity).collect(Collectors.toSet()))
            );
        } catch (final DataIntegrityViolationException e) {
            log.error("One or more players already exist", e);

            throw new PlayerAlreadyExistsException("One or more players could not be added because they already exist.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player updatePlayer(final Player player) throws  PlayerNotFoundException{
        if (PlayerUtils.checkIfPlayerIsMissingID(player)) throw new PlayerNotFoundException("You must provide existing player ID.");

        return this.playerRepository.updatePlayer(player)
            .map(PlayerEntity::toModel)
            .orElseThrow(() -> new PlayerNotFoundException(String.format("Player with ID: %d, NAME: %s, SURNAME: %s not found.", player.getId(), player.getName(), player.getSurname())));
    }

}
