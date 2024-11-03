package com.lboric.soccerdnd.services;

import java.util.Set;

import com.lboric.soccerdnd.exceptions.PlayerAlreadyExistsException;
import com.lboric.soccerdnd.exceptions.PlayerNotFoundException;
import com.lboric.soccerdnd.models.Player;

/**
 * Interface defining the contract for player-related operations.
 * This service provides methods for retrieving, adding, and updating player data.
 */
public interface PlayerService {

    /**
     * Retrieves a player by their unique identifier.
     *
     * @param id the unique identifier of the player
     * @return the Player object associated with the given id
     * @throws PlayerNotFoundException if no player is found with the specified id
     */
    Player getPlayerById(Long id) throws PlayerNotFoundException;

    /**
     * Retrieves all players in the system.
     *
     * @return a set of Player objects representing all players
     */
    Set<Player> getAllPlayers();

    /**
     * Adds a new player to the system.
     *
     * @param player the Player object to be added
     * @return the Player object that was added
     * @throws PlayerAlreadyExistsException if a player with the same identifier already exists
     */
    Player addPlayer(Player player) throws PlayerAlreadyExistsException;

    /**
     * Adds multiple players to the system.
     *
     * @param players a set of Player objects to be added
     * @return a set of Player objects that were added
     * @throws PlayerAlreadyExistsException if one or more players already exist
     */
    Set<Player> addMultiplePlayers(Set<Player> players) throws PlayerAlreadyExistsException;

    /**
     * Updates the information of an existing player.
     *
     * @param player the Player object containing updated information
     * @return the updated Player object
     * @throws PlayerNotFoundException if the player does not exist
     */
    Player updatePlayer(Player player) throws PlayerNotFoundException;

    void deletePlayer(Long id);

}
