package com.lboric.soccerdnd.exceptions;

import jakarta.persistence.EntityNotFoundException;

/**
 * Exception thrown when a player is not found in the system.
 *
 * <p>
 * This exception is typically thrown when a player is searched for by ID or other
 * criteria, but the player does not exist in the database. It extends {@link EntityNotFoundException},
 * which is a standard exception for situations where an entity could not be found.
 * </p>
 *
 * <p>
 * The exception message provides more details about the specific player that could not be found.
 * </p>
 */
public class PlayerNotFoundException extends EntityNotFoundException {

    /**
     * Constructs a new {@link PlayerNotFoundException} with the specified detail message.
     *
     * @param message the detail message explaining why the exception is thrown
     */
    public PlayerNotFoundException(final String message) {
        super(message);
    }

}
