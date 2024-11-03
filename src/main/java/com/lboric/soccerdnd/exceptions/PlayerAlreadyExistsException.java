package com.lboric.soccerdnd.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class PlayerAlreadyExistsException extends DataIntegrityViolationException {

    public PlayerAlreadyExistsException(final String message) {
        super(message);
    }


}
