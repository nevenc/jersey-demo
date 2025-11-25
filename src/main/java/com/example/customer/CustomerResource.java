package com.example.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Collection;

/**
 * REST resource for managing customer operations.
 *
 * <p>This class provides RESTful endpoints for customer management, including:
 * <ul>
 *   <li>Retrieving all customers (GET /customer)</li>
 *   <li>Retrieving a specific customer by ID (GET /customer/{id})</li>
 *   <li>Creating a new customer (POST /customer)</li>
 *   <li>Updating an existing customer (PUT /customer/{id})</li>
 *   <li>Deleting a customer (DELETE /customer/{id})</li>
 * </ul>
 * </p>
 *
 * <p>This resource is registered as a Spring component and uses Jersey JAX-RS
 * annotations for RESTful endpoint mapping.</p>
 *
 * @author Neven C (nevenc)
 * @version 1.0
 * @since 2025
 */
@Component
@Path(("/customer"))
public class CustomerResource {

    /** Logger instance for logging customer resource operations. */
    private static final Logger log = LoggerFactory.getLogger(CustomerResource.class);

    /** Repository for customer data persistence and retrieval. */
    private final CustomerRepository customerRepository;

    /**
     * Constructs a CustomerResource with the specified repository.
     *
     * @param customerRepository the customer repository for data access
     */
    public CustomerResource(CustomerRepository customerRepository) {
        log.info("Initializing CustomerResource.");
        this.customerRepository = customerRepository;
    }

    /**
     * Retrieves all customers.
     *
     * @return a collection of all customers in JSON format
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Customer> findAll() {
        log.info("Fetching all customers");
        return customerRepository.findAll();
    }

    /**
     * Retrieves a customer by their unique identifier.
     *
     * @param id the customer ID to retrieve
     * @return the customer with the specified ID
     * @throws CustomerNotFoundException if customer is not found
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Customer findById(@PathParam("id") Long id) {
        log.info("Fetching customer with id: {}", id);
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    /**
     * Creates a new customer.
     *
     * @param customer the customer data to create
     * @return a Response with status 201 (CREATED) and the created customer
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCustomer(Customer customer) {
        log.info("Creating new customer: {}", customer.getName());
        Customer savedCustomer = customerRepository.save(customer);
        return Response.status(Response.Status.CREATED).entity(savedCustomer).build();
    }

    /**
     * Updates an existing customer.
     *
     * @param id the customer ID to update
     * @param customer the updated customer data
     * @return the updated customer
     * @throws CustomerNotFoundException if customer is not found
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Customer updateCustomer(@PathParam("id") Long id, Customer customer) {
        log.info("Updating customer with id: {}", id);
        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException(id);
        }
        customer.setId(id);
        return customerRepository.save(customer);
    }

    /**
     * Deletes a customer by their unique identifier.
     *
     * @param id the customer ID to delete
     * @return a Response with status 204 (NO_CONTENT) if successful, or 404 if not found
     */
    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") Long id) {
        log.info("Deleting customer with id: {}", id);
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

}
