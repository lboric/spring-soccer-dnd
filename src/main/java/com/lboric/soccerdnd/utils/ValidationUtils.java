package com.lboric.soccerdnd.utils;

import com.lboric.soccerdnd.models.Player;

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

}
