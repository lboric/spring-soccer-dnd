package com.lboric.soccerdnd.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lboric.soccerdnd.entities.PlayerEntity;

@Repository
public interface PlayerRepository extends CrudRepository<PlayerEntity, Long> {
}
