package com.lboric.soccerdnd.utils;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.lboric.soccerdnd.entities.PlayerEntity;
import com.lboric.soccerdnd.models.Player;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerUtils {

    public static boolean checkIfPlayerIsMissingID(final Long id) {
        return Objects.isNull(id);
    }

    public static boolean checkIfPlayerIsMissingID(final Player player) {
        return Objects.isNull(player ) || checkIfPlayerIsMissingID(player.getId());
    }

    public static Set<Player> convertEntitiesToModels(final Iterable<PlayerEntity> playerEntities) {
        return StreamSupport.stream(playerEntities.spliterator(), false)
            .map(PlayerEntity::toModel)
            .collect(Collectors.toSet());
    }

}
