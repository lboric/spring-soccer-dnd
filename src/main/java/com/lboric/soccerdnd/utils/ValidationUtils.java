package com.lboric.soccerdnd.utils;

import com.lboric.soccerdnd.models.Player;
import com.lboric.soccerdnd.models.PlayerStats;

import io.micrometer.common.util.StringUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationUtils {

    public static boolean checkIfPlayerIsMissingID(final Long id) {
        return id == null;
    }

    public static boolean checkIfPlayerIsMissingID(final Player player) {
        return player == null || checkIfPlayerIsMissingID(player.getId());
    }

    public static boolean checkIfPlayerStatsIsMissingPlayerNameOrSurname(final PlayerStats playerStats) {
        return playerStats == null || StringUtils.isBlank(playerStats.getName()) || StringUtils.isBlank(playerStats.getSurname());
    }

    public static boolean checkIfPlayerStatsIsMissingPlayerNameOrSurnameOrSeasonYear(final PlayerStats playerStats) {
        return checkIfPlayerStatsIsMissingPlayerNameOrSurname(playerStats) || playerStats.getSeasonYear() == 0;
    }

}
