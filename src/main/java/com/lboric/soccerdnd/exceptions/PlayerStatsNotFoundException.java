package com.lboric.soccerdnd.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class PlayerStatsNotFoundException extends EntityNotFoundException {

    public PlayerStatsNotFoundException(final String message) {
        super(message);
    }

}
