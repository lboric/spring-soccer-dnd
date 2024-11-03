package com.lboric.soccerdnd.services;

import java.util.Set;

import com.lboric.soccerdnd.models.Player;

public interface PlayerService {

    /**
     * Retrieves a player by their unique identifier.
     *
     * @param id the unique identifier of the player
     * @return the Player object associated with the given id
     */
    Player getPlayerById(Long id);

    Set<Player> getAllPlayers();

    Player addPlayer(Player player);

    Set<Player> addMultiplePlayers(Set<Player> player);

    Player updatePlayer(Player player);

}
