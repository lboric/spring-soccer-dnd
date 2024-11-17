package com.lboric.soccerdnd.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class PlayerStatsAlreadyExistsException extends DataIntegrityViolationException {

    public PlayerStatsAlreadyExistsException(final String message) {
        super(message);
    }

}
