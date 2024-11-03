package com.lboric.soccerdnd.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lboric.soccerdnd.dtos.PlayerStatsDTO;
import com.lboric.soccerdnd.entities.PlayerStatsEntity;

@Repository
public interface PlayerStatsRepository extends CrudRepository<PlayerStatsEntity, Long> {

    @Query("SELECT ps FROM PlayerStatsEntity ps JOIN ps.player p WHERE p.name = ?1")
    List<PlayerStatsDTO> findAllPlayerStatsWithNames();

}
