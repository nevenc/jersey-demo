package com.example.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * Exception mapper for CustomerNotFoundException.
 *
 * <p>This provider is automatically invoked by Jersey when a
 * {@link CustomerNotFoundException} is thrown from a resource method.
 * It converts the exception into a standardized HTTP 404 response with
 * a JSON error message.</p>
 *
 * <p>The response includes:</p>
 * <ul>
 *   <li>HTTP status: 404 Not Found</li>
 *   <li>Content-Type: application/json</li>
 *   <li>Body: {@link ErrorResponse} with status, message, and timestamp</li>
 * </ul>
 *
 * @author Neven C (nevenc)
 * @version 1.0
 * @since 2025
 * @see CustomerNotFoundException
 * @see ErrorResponse
 * @see ExceptionMapper
 */
@Provider
public class CustomerNotFoundExceptionMapper implements ExceptionMapper<CustomerNotFoundException> {

    /** Logger instance for logging exception handling. */
    private static final Logger log = LoggerFactory.getLogger(CustomerNotFoundExceptionMapper.class);

    /**
     * Converts a CustomerNotFoundException into an HTTP 404 response.
     *
     * <p>This method is automatically called by Jersey when a CustomerNotFoundException
     * is thrown. It logs the exception and returns a properly formatted error response
     * with HTTP 404 status and JSON error details.</p>
     *
     * @param exception the CustomerNotFoundException to map
     * @return a Response with HTTP 404 status and error details in JSON format
     */
    @Override
    public Response toResponse(CustomerNotFoundException exception) {
        log.warn("Customer not found: {}", exception.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                Response.Status.NOT_FOUND.getStatusCode(),
                exception.getMessage()
        );

        return Response
                .status(Response.Status.NOT_FOUND)
                .type(MediaType.APPLICATION_JSON)
                .entity(errorResponse)
                .build();
    }
}
