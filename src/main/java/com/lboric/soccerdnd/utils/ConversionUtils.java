package com.lboric.soccerdnd.utils;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.lboric.soccerdnd.entities.PlayerEntity;
import com.lboric.soccerdnd.models.Player;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Utility class for converting between different object types.
 *
 * <p>
 * This class provides methods to convert entity objects into model objects, particularly
 * for transforming {@link PlayerEntity} objects to {@link Player} model objects.
 * The methods in this class help to streamline the conversion process and ensure that
 * data is passed in the correct format across layers (e.g., from the database to the business logic).
 * </p>
 *
 * <p>
 * This class is marked as {@link NoArgsConstructor} to ensure it cannot be instantiated externally.
 * </p>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConversionUtils {

    /**
     * Converts an {@link Iterable} of {@link PlayerEntity} objects into a {@link Set} of {@link Player} model objects.
     *
     * <p>
     * This method iterates over the given {@link Iterable} of {@link PlayerEntity} objects,
     * maps each {@link PlayerEntity} to a {@link Player} model using the {@link PlayerEntity#toModel()} method,
     * and collects the results into a {@link Set}.
     * </p>
     *
     * @param playerEntities the {@link Iterable} collection of {@link PlayerEntity} objects to convert
     * @return a {@link Set} of {@link Player} objects, each representing a player
     *         from the corresponding {@link PlayerEntity} in the input
     */
    public static Set<Player> convertEntitiesToModels(final Iterable<PlayerEntity> playerEntities) {
        return StreamSupport.stream(playerEntities.spliterator(), false)
            .map(PlayerEntity::toModel)
            .collect(Collectors.toSet());
    }

}
