package com.lboric.soccerdnd.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lboric.soccerdnd.entities.PlayerEntity;
import com.lboric.soccerdnd.models.Player;

@Repository
public interface PlayerRepository extends CrudRepository<PlayerEntity, Long> {

    @Transactional
    default Optional<PlayerEntity> updatePlayer(final Player updatedPlayer) {
        return this.findById(updatedPlayer.getId()).map(player -> {
            player.setName(updatedPlayer.getName());
            player.setSurname(updatedPlayer.getSurname());

            return this.save(player);
        });
    }

}
