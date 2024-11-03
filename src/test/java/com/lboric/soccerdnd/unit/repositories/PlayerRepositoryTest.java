package com.lboric.soccerdnd.unit.repositories;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.lboric.soccerdnd.entities.PlayerEntity;
import com.lboric.soccerdnd.repositories.PlayerRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PlayerRepositoryTest {

    private final PlayerRepository playerRepository;

    @Autowired
    PlayerRepositoryTest(final PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Test
    @Order(1)
    @Rollback(false)
    @DisplayName("GIVEN a new player, WHEN saved, THEN it should be persisted correctly")
    void testSavePlayer() {
        final PlayerEntity playerEntity = new PlayerEntity(null, "Neymar", "Jr");
        final PlayerEntity savedPlayer = this.playerRepository.save(playerEntity);

        assertAll(
          () -> assertThat(savedPlayer).isNotNull(),
          () -> assertThat(savedPlayer.getId()).isEqualTo(4L),
          () -> assertThat(savedPlayer.getName()).isEqualTo("Neymar"),
          () -> assertThat(savedPlayer.getSurname()).isEqualTo("Jr")
        );
    }

    @Test
    @Order(2)
    @DisplayName("GIVEN an existing player ID, WHEN retrieved, THEN it should return the correct player")
    void testFindById() {
        final long playerId = 4L;
        final Optional<PlayerEntity> player = this.playerRepository.findById(playerId);

        assertAll(
          () -> assertThat(player).isPresent(),
          () -> assertThat(player.get().getId()).isEqualTo(playerId),
          () -> assertThat(player.get().getName()).isEqualTo("Neymar"),
          () -> assertThat(player.get().getSurname()).isEqualTo("Jr")
        );
    }

    @Test
    @Order(3)
    @DisplayName("GIVEN an existing player, WHEN deleted, THEN it should not be found anymore")
    void testDeletePlayer() {
        final long playerId = 4L;
        final Optional<PlayerEntity> playerToDelete = this.playerRepository.findById(playerId);

        playerToDelete.ifPresent(this.playerRepository::delete);

        assertThat(this.playerRepository.findById(playerId)).isNotPresent();
    }

}
