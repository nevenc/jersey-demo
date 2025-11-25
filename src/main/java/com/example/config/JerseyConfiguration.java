package com.example.config;

import com.example.customer.CustomerNotFoundExceptionMapper;
import com.example.customer.CustomerResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import jakarta.ws.rs.ApplicationPath;

/**
 * Spring configuration class for Jersey (JAX-RS) framework integration.
 *
 * <p>This configuration class sets up the Jersey servlet and registers all JAX-RS
 * resource classes and exception mappers. It extends {@link ResourceConfig}
 * to leverage Jersey's resource management capabilities within a Spring Boot application.</p>
 *
 * <p><strong>API Base Path:</strong> All REST endpoints are accessible under {@code /api}
 * (e.g., {@code http://localhost:8080/api/customer})</p>
 *
 * <p><strong>Registered Resources:</strong></p>
 * <ul>
 *   <li>{@link CustomerResource} - Handles all customer-related CRUD operations</li>
 * </ul>
 *
 * <p><strong>Registered Exception Mappers:</strong></p>
 * <ul>
 *   <li>{@link CustomerNotFoundExceptionMapper} - Maps CustomerNotFoundException to HTTP 404</li>
 * </ul>
 *
 * <p>The configuration is automatically discovered and instantiated by Spring Boot
 * through the {@code @Configuration} annotation, and the Jersey servlet is registered
 * at the application path specified by {@code @ApplicationPath}.</p>
 *
 * @author Neven C (nevenc)
 * @version 1.0
 * @since 2025
 * @see ResourceConfig
 * @see ApplicationPath
 * @see CustomerResource
 * @see CustomerNotFoundExceptionMapper
 */
@Configuration
@ApplicationPath("/api")
public class JerseyConfiguration extends ResourceConfig {

    /** Logger instance for logging Jersey configuration events. */
    private static final Logger log = LoggerFactory.getLogger(JerseyConfiguration.class);

    /**
     * Initializes the Jersey configuration by registering JAX-RS resource classes and exception mappers.
     *
     * <p>This constructor is automatically invoked by Spring during application startup.
     * It registers all REST resource classes and exception mapper providers that will
     * handle incoming HTTP requests and exceptions respectively.</p>
     *
     * <p>Log output at INFO level helps identify when Jersey configuration is complete:</p>
     * <pre>
     * INFO - Configuring JerseyConfiguration, registering components with JAX-RS.
     * </pre>
     */
    public JerseyConfiguration() {
        log.info("Configuring JerseyConfiguration, registering components with JAX-RS.");
        register(CustomerResource.class);
        register(CustomerNotFoundExceptionMapper.class);
    }
}
