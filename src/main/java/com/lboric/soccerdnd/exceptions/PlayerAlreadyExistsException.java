package com.lboric.soccerdnd.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * Exception thrown when a player already exists in the system.
 *
 * <p>
 * This exception is typically used in scenarios where an attempt is made to insert
 * a player into the database who already exists, as determined by unique constraints
 * on the player's attributes (e.g., name and surname).
 * </p>
 *
 * <p>
 * Extends {@link DataIntegrityViolationException} to indicate a violation of database integrity
 * due to the attempted insertion of duplicate player data.
 * </p>
 */
public class PlayerAlreadyExistsException extends DataIntegrityViolationException {

    /**
     * Constructs a new {@link PlayerAlreadyExistsException} with the specified detail message.
     *
     * @param message the detail message that explains the reason for the exception
     */
    public PlayerAlreadyExistsException(final String message) {
        super(message);
    }

}
