package com.lboric.soccerdnd.services;

import java.util.Set;

import com.lboric.soccerdnd.exceptions.PlayerAlreadyExistsException;
import com.lboric.soccerdnd.exceptions.PlayerNotFoundException;
import com.lboric.soccerdnd.exceptions.PlayerStatsAlreadyExistsException;
import com.lboric.soccerdnd.exceptions.PlayerStatsNotFoundException;
import com.lboric.soccerdnd.models.PlayerStats;

/**
 * Interface defining the contract for player-related operations.
 * This service provides methods for retrieving, adding, and updating player stats data.
 */
public interface PlayerStatsService {

    /**
     * Retrieves the statistics for a specific player by their ID.
     *
     * @param id the ID of the player whose statistics are to be retrieved
     * @return the PlayerStats object for the specified player
     * @throws PlayerNotFoundException if the player ID is invalid or does not exist
     * @throws PlayerStatsNotFoundException if no statistics are found for the specified player ID
     */
    PlayerStats getPlayerStatsById(Long id) throws PlayerNotFoundException, PlayerStatsNotFoundException;

    /**
     * Retrieves the statistics for all players.
     *
     * @return a set of PlayerStats objects for all players, or an empty set if no players have statistics
     */
    Set<PlayerStats> getAllPlayersStats();

    /**
     * Adds new statistics for a player.
     *
     * @param playerStats the PlayerStats object containing the player's statistics to be added
     * @return the added PlayerStats object
     * @throws IllegalArgumentException if the provided PlayerStats object is missing required fields
     * @throws PlayerAlreadyExistsException if the player associated with the statistics does not exist
     * @throws PlayerStatsAlreadyExistsException if statistics for the player and season already exist
     */
    PlayerStats addPlayerStats(PlayerStats playerStats)
      throws IllegalArgumentException, PlayerAlreadyExistsException, PlayerStatsAlreadyExistsException;

    /**
     * Updates the statistics for an existing player.
     *
     * @param playerStats the PlayerStats object containing the updated statistics
     * @return the updated PlayerStats object
     * @throws PlayerStatsNotFoundException if no statistics are found for the specified player and season
     * @throws PlayerNotFoundException if the player associated with the statistics does not exist
     */
    PlayerStats updatePlayerStats(PlayerStats playerStats)
      throws PlayerStatsNotFoundException, PlayerNotFoundException;

    /**
     * Deletes the statistics for a specific player based on their name, surname, and season year.
     *
     * @param playerStats the PlayerStats object containing the identifying details of the statistics to be deleted
     * @throws IllegalArgumentException if the provided PlayerStats object is missing required fields
     * @throws PlayerNotFoundException if the player does not exist or their statistics are not found
     * @throws PlayerStatsNotFoundException if no statistics exist for the specified player and season
     */
    void deletePlayerStatsByNameSurnameAndSeasonYear(PlayerStats playerStats) throws IllegalArgumentException, PlayerNotFoundException, PlayerStatsNotFoundException;

}
