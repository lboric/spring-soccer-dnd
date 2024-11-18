package com.lboric.soccerdnd.utils;

import com.lboric.soccerdnd.models.Player;
import com.lboric.soccerdnd.models.PlayerStats;

import io.micrometer.common.util.StringUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Utility class for performing validation checks on {@link Player} and {@link PlayerStats} objects.
 *
 * <p>
 * This class contains static methods used for checking whether certain required fields are missing or invalid.
 * These methods are designed to ensure that objects have the necessary attributes to function properly.
 * </p>
 *
 * <p>
 * The class is marked as {@link NoArgsConstructor} to prevent instantiation, as it is intended solely for utility purposes.
 * </p>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationUtils {

    /**
     * Checks if the provided player ID is missing (i.e., {@code null}).
     *
     * @param id the ID of the player to check
     * @return {@code true} if the ID is {@code null}, {@code false} otherwise
     */
    public static boolean checkIfPlayerIsMissingID(final Long id) {
        return id == null;
    }

    /**
     * Checks if the provided {@link Player} object is missing its ID.
     *
     * <p>
     * This method checks if the player object is {@code null} or if the player's ID is {@code null}.
     * </p>
     *
     * @param player the {@link Player} object to check
     * @return {@code true} if the player object or its ID is {@code null}, {@code false} otherwise
     */
    public static boolean checkIfPlayerIsMissingID(final Player player) {
        return player == null || checkIfPlayerIsMissingID(player.getId());
    }

    /**
     * Checks if the provided {@link PlayerStats} object is missing a player name or surname.
     *
     * <p>
     * This method checks if the player stats object is {@code null}, or if the name or surname of the player is blank.
     * </p>
     *
     * @param playerStats the {@link PlayerStats} object to check
     * @return {@code true} if the player stats object is {@code null} or if the player name or surname is blank,
     *         {@code false} otherwise
     */
    public static boolean checkIfPlayerStatsIsMissingPlayerNameOrSurname(final PlayerStats playerStats) {
        return playerStats == null || StringUtils.isBlank(playerStats.getName()) || StringUtils.isBlank(playerStats.getSurname());
    }

    /**
     * Checks if the provided {@link PlayerStats} object is missing a player name, surname, or season year.
     *
     * <p>
     * This method checks if the player stats object is missing the player name, surname, or if the season year is set to zero.
     * </p>
     *
     * @param playerStats the {@link PlayerStats} object to check
     * @return {@code true} if the player stats object is missing a name, surname, or season year,
     *         {@code false} otherwise
     */
    public static boolean checkIfPlayerStatsIsMissingPlayerNameOrSurnameOrSeasonYear(final PlayerStats playerStats) {
        return checkIfPlayerStatsIsMissingPlayerNameOrSurname(playerStats) || playerStats.getSeasonYear() == 0;
    }

}
