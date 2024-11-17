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

@Repository
public interface PlayerStatsRepository extends JpaRepository<PlayerStatsEntity, Long> {

    @Query("SELECT new com.lboric.soccerdnd.dtos.PlayerStatsDTO(p.id, p.name, p.surname, ps.seasonYear, ps.numberOfGoals) FROM PlayerStatsEntity ps JOIN ps.player p")
    Optional<List<PlayerStatsDTO>> findAllPlayerStats();

    @Query("SELECT new com.lboric.soccerdnd.dtos.PlayerStatsDTO(p.id, p.name, p.surname, ps.seasonYear, ps.numberOfGoals) FROM PlayerStatsEntity ps JOIN ps.player p WHERE p.id = :playerId")
    Optional<PlayerStatsDTO> findPlayerStatsById(@Param("playerId") Long playerId);

    @Query("SELECT ps FROM PlayerStatsEntity ps WHERE ps.player = :player AND ps.seasonYear = :seasonYear")
    Optional<PlayerStatsEntity> findByPlayerAndSeasonYear(@Param("player") PlayerEntity player, @Param("seasonYear") int seasonYear);

    @Modifying
    @Transactional
    @Query("DELETE FROM PlayerStatsEntity ps WHERE ps.player.name = :name AND ps.player.surname = :surname AND ps.seasonYear = :seasonYear")
    void deleteByNameAndSurnameAndSeasonYear(@Param("name") String name, @Param("surname") String surname, @Param("seasonYear") int seasonYear);

}
