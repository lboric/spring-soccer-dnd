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

/**
 * Implementation of the {@link PlayerService} interface that handles operations related to Player entities.
 * This service provides methods for retrieving, adding, and updating player data.
 * It interacts with the {@link PlayerRepository} to perform database operations
 * and utilizes custom exceptions to handle specific error scenarios.
 *
 * <p>
 * The following operations are supported:
 * <ul>
 *   <li>Retrieve a single player by their ID.</li>
 *   <li>Retrieve all players.</li>
 *   <li>Add a new player to the repository.</li>
 *   <li>Add multiple players in one operation.</li>
 *   <li>Update an existing player's information.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Each method throws specific exceptions such as {@link PlayerNotFoundException}
 * and {@link PlayerAlreadyExistsException} to indicate failure scenarios,
 * ensuring clear communication of issues to the calling layers.
 * </p>
 *
 * <p>
 * This class also contains logging for important actions and errors, aiding in debugging and
 * operational monitoring.
 * </p>
 */
@Slf4j
@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    PlayerServiceImpl(final PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    /**
     *
     * {@inheritDoc}
     *
     */
    @Override
    public Player getPlayerById(final Long id) throws PlayerNotFoundException {
        if (PlayerUtils.checkIfPlayerIsMissingID(id)) throw new PlayerNotFoundException("You must provide an existing player ID.");

        return this.playerRepository.findById(id)
            .map(PlayerEntity::toModel)
            .orElseThrow(() -> new PlayerNotFoundException(String.format("Player with id: %d not found.", id)));
    }

    /**
     *
     * {@inheritDoc}
     *
     */
    @Override
    public Set<Player> getAllPlayers() {
        return PlayerUtils.convertEntitiesToModels(this.playerRepository.findAll());
    }

    /**
     *
     * {@inheritDoc}
     *
     */
    @Override
    public Player addPlayer(final Player player) throws PlayerAlreadyExistsException {
        try {
            return this.playerRepository.save(player.toEntity()).toModel();
        } catch (final DataIntegrityViolationException e) {
            log.error("Player {} {} already exists", player.getName(), player.getSurname(), e);

            throw new PlayerAlreadyExistsException(String.format("Player with name: %s and surname: %s already exists.", player.getName(), player.getSurname()));
        }
    }

    /**
     *
     * {@inheritDoc}
     *
     */
    @Override
    public Set<Player> addMultiplePlayers(final Set<Player> players) throws PlayerAlreadyExistsException {
        try {
            return PlayerUtils.convertEntitiesToModels(this.playerRepository.saveAll(
              players.stream().map(Player::toEntity).collect(Collectors.toSet()))
            );
        } catch (final DataIntegrityViolationException e) {
            log.error("One or more players could not be added", e);

            throw new PlayerAlreadyExistsException("One or more players could not be added because they already exist.");
        }
    }

    /**
     *
     * {@inheritDoc}
     *
     */
    @Override
    public Player updatePlayer(final Player player) throws PlayerNotFoundException {
        if (PlayerUtils.checkIfPlayerIsMissingID(player)) throw new PlayerNotFoundException("You must provide an existing player ID.");

        return this.playerRepository.updatePlayer(player)
            .map(PlayerEntity::toModel)
            .orElseThrow(() -> new PlayerNotFoundException(String.format("Player with id: %d, name: %s, surname: %s not found.", player.getId(), player.getName(), player.getSurname())));
    }

    /**
     *
     * {@inheritDoc}
     *
     */
    @Override
    public void deletePlayer(final Long id) throws PlayerNotFoundException {
        if (PlayerUtils.checkIfPlayerIsMissingID(id)) throw new PlayerNotFoundException("You must provide an existing player ID.");

        this.playerRepository.deleteById(id);
    }

}
