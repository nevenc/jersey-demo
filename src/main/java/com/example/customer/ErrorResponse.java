package com.example.customer;

import java.time.LocalDateTime;

/**
 * Standard error response DTO for REST API errors.
 *
 * <p>This class represents a standardized error response format that is returned
 * by all exception mappers and error handlers. It includes the HTTP status code,
 * a human-readable error message, and a timestamp for logging and debugging purposes.</p>
 *
 * @author Neven C (nevenc)
 * @version 1.0
 * @since 2025
 */
public class ErrorResponse {

    /** The HTTP status code associated with the error. */
    private int status;

    /** A human-readable error message describing what went wrong. */
    private String message;

    /** The timestamp when the error occurred. */
    private LocalDateTime timestamp;

    /**
     * Default constructor for JSON deserialization.
     */
    public ErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Constructs an ErrorResponse with status code and message.
     *
     * @param status the HTTP status code
     * @param message the error message
     */
    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Gets the HTTP status code.
     *
     * @return the status code
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets the HTTP status code.
     *
     * @param status the status code to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Gets the error message.
     *
     * @return the error message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the error message.
     *
     * @param message the error message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the timestamp when the error occurred.
     *
     * @return the timestamp
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp when the error occurred.
     *
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
