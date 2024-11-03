package com.lboric.soccerdnd.utils;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.lboric.soccerdnd.entities.PlayerEntity;
import com.lboric.soccerdnd.models.Player;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConversionUtils {

    public static Set<Player> convertEntitiesToModels(final Iterable<PlayerEntity> playerEntities) {
        return StreamSupport.stream(playerEntities.spliterator(), false)
            .map(PlayerEntity::toModel)
            .collect(Collectors.toSet());
    }

}
