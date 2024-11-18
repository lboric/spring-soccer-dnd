package com.lboric.soccerdnd.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lboric.soccerdnd.dtos.PlayerStatsDTO;
import com.lboric.soccerdnd.entities.PlayerEntity;
import com.lboric.soccerdnd.entities.PlayerStatsEntity;

import jakarta.transaction.Transactional;

/**
 * Repository interface for performing CRUD operations on {@link PlayerStatsEntity} objects.
 *
 * <p>
 * This repository extends {@link JpaRepository} to provide standard JPA operations for interacting with the
 * {@link PlayerStatsEntity} database table. It also includes custom queries to fetch player statistics data
 * and perform other operations related to player statistics.
 * </p>
 */
@Repository
public interface PlayerStatsRepository extends JpaRepository<PlayerStatsEntity, Long> {

    /**
     * Retrieves the statistics for all players, converting them into {@link PlayerStatsDTO}.
     *
     * <p>
     * This query joins the {@link PlayerStatsEntity} with the {@link PlayerEntity} to fetch the player's
     * statistics, including the player's ID, name, surname, season year, and the number of goals scored.
     * </p>
     *
     * @return an {@link Optional} containing a list of {@link PlayerStatsDTO} objects, or an empty {@link Optional}
     *         if no player statistics are found
     */
    @Query("SELECT new com.lboric.soccerdnd.dtos.PlayerStatsDTO(p.id, p.name, p.surname, ps.seasonYear, ps.numberOfGoals) FROM PlayerStatsEntity ps JOIN ps.player p")
    Optional<List<PlayerStatsDTO>> findAllPlayerStats();

    /**
     * Retrieves the statistics for a specific player by their ID, converting the result into {@link PlayerStatsDTO}.
     *
     * <p>
     * This query fetches the statistics for the player with the given {@code playerId}, including their ID,
     * name, surname, season year, and the number of goals scored.
     * </p>
     *
     * @param playerId the unique ID of the player whose statistics are to be retrieved
     * @return an {@link Optional} containing the {@link PlayerStatsDTO} of the player, or an empty {@link Optional}
     *         if no statistics are found for the player
     */
    @Query("SELECT new com.lboric.soccerdnd.dtos.PlayerStatsDTO(p.id, p.name, p.surname, ps.seasonYear, ps.numberOfGoals) FROM PlayerStatsEntity ps JOIN ps.player p WHERE p.id = :playerId")
    Optional<PlayerStatsDTO> findPlayerStatsById(@Param("playerId") Long playerId);

    /**
     * Finds player statistics by the given player and season year.
     *
     * <p>
     * This query retrieves the statistics of a player in a particular season, identified by the player's entity
     * and the season year.
     * </p>
     *
     * @param player the {@link PlayerEntity} representing the player whose statistics are being searched
     * @param seasonYear the season year for which the statistics are being retrieved
     * @return an {@link Optional} containing the {@link PlayerStatsEntity} if the statistics exist, or an empty
     *         {@link Optional} if no statistics are found for the player in that season
     */
    @Query("SELECT ps FROM PlayerStatsEntity ps WHERE ps.player = :player AND ps.seasonYear = :seasonYear")
    Optional<PlayerStatsEntity> findByPlayerAndSeasonYear(@Param("player") PlayerEntity player, @Param("seasonYear") int seasonYear);

    /**
     * Deletes player statistics based on the player's name, surname, and season year.
     *
     * <p>
     * This query removes the player statistics for the player identified by their name, surname, and the
     * specified season year.
     * </p>
     *
     * @param name the first name of the player
     * @param surname the last name of the player
     * @param seasonYear the season year of the statistics to be deleted
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM PlayerStatsEntity ps WHERE ps.player.name = :name AND ps.player.surname = :surname AND ps.seasonYear = :seasonYear")
    void deleteByNameAndSurnameAndSeasonYear(@Param("name") String name, @Param("surname") String surname, @Param("seasonYear") int seasonYear);

}
