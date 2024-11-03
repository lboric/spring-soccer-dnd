package com.lboric.soccerdnd.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class PlayerNotFoundException extends EntityNotFoundException {
    public PlayerNotFoundException(final String message) {
        super(message);
    }
}
