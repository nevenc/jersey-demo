package com.example.customer;

/**
 * Exception thrown when a customer is not found in the database.
 *
 * <p>This is a custom runtime exception that is used to signal when a requested
 * customer resource does not exist. It is caught and handled by the
 * {@link CustomerNotFoundExceptionMapper} to return an appropriate HTTP 404
 * response with a descriptive error message.</p>
 *
 * @author Neven C (nevenc)
 * @version 1.0
 * @since 2025
 */
public class CustomerNotFoundException extends RuntimeException {

    /**
     * Constructs a CustomerNotFoundException with the specified customer ID.
     *
     * @param id the customer ID that was not found
     */
    public CustomerNotFoundException(Long id) {
        super("Customer with id " + id + " not found");
    }

    /**
     * Constructs a CustomerNotFoundException with a custom message.
     *
     * @param message the error message
     */
    public CustomerNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a CustomerNotFoundException with a custom message and cause.
     *
     * @param message the error message
     * @param cause the underlying cause
     */
    public CustomerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
