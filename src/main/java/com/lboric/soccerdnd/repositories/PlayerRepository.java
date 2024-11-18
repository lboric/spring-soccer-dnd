package com.lboric.soccerdnd.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lboric.soccerdnd.entities.PlayerEntity;
import com.lboric.soccerdnd.models.Player;

/**
 * Repository interface for performing CRUD operations on {@link PlayerEntity} objects.
 *
 * <p>
 * This interface extends {@link CrudRepository}, allowing basic CRUD operations such as save, find, and delete.
 * Additional custom query methods are also provided to interact with the database for specific queries.
 * </p>
 */
@Repository
public interface PlayerRepository extends CrudRepository<PlayerEntity, Long> {

    /**
     * Updates an existing player in the repository with the details from the provided {@link Player} object.
     *
     * <p>
     * The method checks if the player with the given ID exists, and if so, updates the player's name and surname.
     * The updated player is then saved back to the database.
     * </p>
     *
     * @param updatedPlayer the {@link Player} object containing the updated player information
     * @return an {@link Optional} containing the updated {@link PlayerEntity} if the player was found and updated,
     *         or an empty {@link Optional} if no player with the given ID exists
     */
    @Modifying
    @Transactional
    default Optional<PlayerEntity> updatePlayer(final Player updatedPlayer) {
        return Optional.ofNullable(updatedPlayer.getId())
            .flatMap(this::findById)
            .map(player -> {
               player.setName(updatedPlayer.getName());
               player.setSurname(updatedPlayer.getSurname());

               return this.save(player);
            });
    }

    /**
     * Finds a player by their name and surname.
     *
     * <p>
     * This query looks for a player in the database with the specified name and surname.
     * It is useful when trying to find a specific player based on these identifying attributes.
     * </p>
     *
     * @param name the name of the player
     * @param surname the surname of the player
     * @return an {@link Optional} containing the {@link PlayerEntity} if the player is found,
     *         or an empty {@link Optional} if no player with the given name and surname exists
     */
    @Query("SELECT p FROM PlayerEntity p WHERE p.name = :name AND p.surname = :surname")
    Optional<PlayerEntity> findByNameAndSurname(@Param("name") String name, @Param("surname") String surname);

}
