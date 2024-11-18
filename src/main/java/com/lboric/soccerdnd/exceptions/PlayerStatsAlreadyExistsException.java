package com.lboric.soccerdnd.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * Exception thrown when player statistics already exist in the system.
 *
 * <p>
 * This exception is typically thrown when an attempt is made to insert player statistics
 * into the database that already exist, as determined by unique constraints (for example,
 * a player’s statistics for a particular season).
 * </p>
 *
 * <p>
 * Extends {@link DataIntegrityViolationException} to indicate a violation of database integrity
 * due to the attempted insertion of duplicate player statistics data.
 * </p>
 */
public class PlayerStatsAlreadyExistsException extends DataIntegrityViolationException {

    /**
     * Constructs a new {@link PlayerStatsAlreadyExistsException} with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public PlayerStatsAlreadyExistsException(final String message) {
        super(message);
    }

}
