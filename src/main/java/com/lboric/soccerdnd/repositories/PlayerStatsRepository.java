package com.lboric.soccerdnd.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lboric.soccerdnd.dtos.PlayerStatsDTO;
import com.lboric.soccerdnd.entities.PlayerStatsEntity;

@Repository
public interface PlayerStatsRepository extends JpaRepository<PlayerStatsEntity, Long> {

    @Query("SELECT new com.lboric.soccerdnd.dtos.PlayerStatsDTO(p.id, p.name, p.surname, ps.seasonYear, ps.numberOfGoals) FROM PlayerStatsEntity ps JOIN ps.player p")
    List<PlayerStatsDTO> findAllPlayerStats();

}