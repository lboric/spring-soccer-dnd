package com.lboric.soccerdnd.exceptions;

import jakarta.persistence.EntityNotFoundException;

/**
 * Exception thrown when player statistics are not found in the system.
 *
 * <p>
 * This exception is typically thrown when an attempt is made to retrieve player statistics
 * for a specific player, but no matching statistics are found in the database.
 * It extends {@link EntityNotFoundException} to represent the failure to find the requested entity.
 * </p>
 *
 * <p>
 * The exception message provides more details about the specific player or statistics
 * that could not be found.
 * </p>
 */
public class PlayerStatsNotFoundException extends EntityNotFoundException {

    /**
     * Constructs a new {@link PlayerStatsNotFoundException} with the specified detail message.
     *
     * @param message the detail message explaining why the exception is thrown
     */
    public PlayerStatsNotFoundException(final String message) {
        super(message);
    }

}
