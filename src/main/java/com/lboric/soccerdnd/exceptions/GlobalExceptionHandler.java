package com.lboric.soccerdnd.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global Exception Handler for the application.
 *
 * <p>
 * This class is responsible for handling various exceptions thrown within the application
 * and returning appropriate HTTP responses with relevant status codes.
 * </p>
 *
 * <p>
 * The handler catches specific exceptions such as:
 * <ul>
 *   <li>{@link PlayerNotFoundException} - for situations where a player is not found.</li>
 *   <li>{@link PlayerAlreadyExistsException} - for situations where a player already exists.</li>
 *   <li>{@link PlayerStatsNotFoundException} - for situations where player statistics are not found.</li>
 *   <li>{@link PlayerStatsAlreadyExistsException} - for situations where player statistics already exist.</li>
 *   <li>{@link RuntimeException} - for general unhandled runtime exceptions.</li>
 * </ul>
 * </p>
 *
 * <p>
 * The responses include relevant HTTP status codes and error messages returned in the response body.
 * </p>
 */
@ControllerAdvice(basePackages = "com.lboric.soccerdnd")
public class GlobalExceptionHandler {

    /**
     * Handles the {@link PlayerNotFoundException}.
     *
     * @param e the exception that was thrown
     * @return a {@link ResponseEntity} containing the error message and a {@code NOT_FOUND} status
     */
    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<String> handlePlayerNotFound(final PlayerNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    /**
     * Handles the {@link PlayerAlreadyExistsException}.
     *
     * @param e the exception that was thrown
     * @return a {@link ResponseEntity} containing the error message and a {@code CONFLICT} status
     */
    @ExceptionHandler(PlayerAlreadyExistsException.class)
    public ResponseEntity<String> handlePlayerAlreadyExists(final PlayerAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    /**
     * Handles the {@link PlayerStatsNotFoundException}.
     *
     * @param e the exception that was thrown
     * @return a {@link ResponseEntity} containing the error message and a {@code NOT_FOUND} status
     */
    @ExceptionHandler(PlayerStatsNotFoundException.class)
    public ResponseEntity<String> handlePlayerStatsNotFound(final PlayerStatsNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    /**
     * Handles the {@link PlayerStatsAlreadyExistsException}.
     *
     * @param e the exception that was thrown
     * @return a {@link ResponseEntity} containing the error message and a {@code CONFLICT} status
     */
    @ExceptionHandler(PlayerStatsAlreadyExistsException.class)
    public ResponseEntity<String> handlePlayerStatsAlreadyExists(final PlayerStatsAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    /**
     * Handles generic runtime exceptions.
     *
     * @return a {@link ResponseEntity} containing a generic error message and an {@code INTERNAL_SERVER_ERROR} status
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
    }

}
