package com.example.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the CustomerResource REST endpoints using TestRestTemplate.
 *
 * <p>These tests verify the behavior of all CRUD operations:
 * <ul>
 *   <li>GET /api/customer - Retrieve all customers</li>
 *   <li>GET /api/customer/{id} - Retrieve a customer by ID</li>
 *   <li>POST /api/customer - Create a new customer</li>
 *   <li>PUT /api/customer/{id} - Update an existing customer</li>
 *   <li>DELETE /api/customer/{id} - Delete a customer</li>
 * </ul>
 * </p>
 *
 * <p>TestRestTemplate is used to test Jersey resources registered with @ApplicationPath
 * against the full HTTP stack without needing a separate test framework.</p>
 *
 * @author Neven C (nevenc)
 * @version 1.0
 * @since 2025
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerResourceTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CustomerRepository customerRepository;

    private static final String BASE_URL = "/api/customer";

    /**
     * Clean up the database before each test.
     */
    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
    }

    /**
     * Test: GET /api/customer - Retrieve all customers
     * Verifies that all customers are returned as JSON array.
     */
    @Test
    void testGetAllCustomers() {
        // Arrange: Create some test customers
        Customer customer1 = new Customer(null, "Jack Doe", "jack.doe@example.com");
        Customer customer2 = new Customer(null, "Jane Doe", "jane.doe@example.com");
        customerRepository.save(customer1);
        customerRepository.save(customer2);

        // Act: Make GET request
        ResponseEntity<Customer[]> response = restTemplate.getForEntity(BASE_URL, Customer[].class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().length);
        assertEquals("Jack Doe", response.getBody()[0].getName());
        assertEquals("jane.doe@example.com", response.getBody()[1].getEmail());
    }

    /**
     * Test: GET /api/customer - Retrieve empty list
     * Verifies that an empty JSON array is returned when no customers exist.
     */
    @Test
    void testGetAllCustomersEmpty() {
        // Act
        ResponseEntity<Customer[]> response = restTemplate.getForEntity(BASE_URL, Customer[].class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().length);
    }

    /**
     * Test: GET /api/customer/{id} - Retrieve a customer by ID
     * Verifies that a specific customer is returned with correct data.
     */
    @Test
    void testGetCustomerById() {
        // Arrange: Create a test customer
        Customer customer = new Customer(null, "John Doe", "john.doe@example.com");
        Customer savedCustomer = customerRepository.save(customer);
        Long customerId = savedCustomer.getId();

        // Act
        ResponseEntity<Customer> response = restTemplate.getForEntity(BASE_URL + "/" + customerId, Customer.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(customerId, response.getBody().getId());
        assertEquals("John Doe", response.getBody().getName());
        assertEquals("john.doe@example.com", response.getBody().getEmail());
    }

    /**
     * Test: GET /api/customer/{id} - Customer not found
     * Verifies that 404 NOT_FOUND is returned when customer ID doesn't exist.
     */
    @Test
    void testGetCustomerByIdNotFound() {
        // Act
        ResponseEntity<Customer> response = restTemplate.getForEntity(BASE_URL + "/999", Customer.class);

        // Assert - Exception mapper returns 404 NOT_FOUND with error response
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Test: POST /api/customer - Create a new customer
     * Verifies that a new customer is created with HTTP 201 status.
     */
    @Test
    void testCreateCustomer() {
        // Arrange: Prepare customer data
        Customer newCustomer = new Customer(null, "Alice Doe", "alice.doe@example.com");

        // Act
        ResponseEntity<Customer> response = restTemplate.postForEntity(
                BASE_URL, newCustomer, Customer.class);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
        assertEquals("Alice Doe", response.getBody().getName());
        assertEquals("alice.doe@example.com", response.getBody().getEmail());

        // Verify the customer was actually saved in the database
        assertEquals(1, customerRepository.count());
    }

    /**
     * Test: POST /api/customer - Create multiple customers
     * Verifies that multiple customers can be created sequentially.
     */
    @Test
    void testCreateMultipleCustomers() {
        // Create first customer
        Customer customer1 = new Customer(null, "Bob Doe", "bob.doe@example.com");
        ResponseEntity<Customer> response1 = restTemplate.postForEntity(
                BASE_URL, customer1, Customer.class);
        assertEquals(HttpStatus.CREATED, response1.getStatusCode());

        // Create second customer
        Customer customer2 = new Customer(null, "Carol Doe", "carol.doe@example.com");
        ResponseEntity<Customer> response2 = restTemplate.postForEntity(
                BASE_URL, customer2, Customer.class);
        assertEquals(HttpStatus.CREATED, response2.getStatusCode());

        // Verify both customers were saved
        assertEquals(2, customerRepository.count());
    }

    /**
     * Test: PUT /api/customer/{id} - Update an existing customer
     * Verifies that a customer is updated with new information.
     */
    @Test
    void testUpdateCustomer() {
        // Arrange: Create a test customer
        Customer customer = new Customer(null, "David Doe", "david.doe@example.com");
        Customer savedCustomer = customerRepository.save(customer);
        Long customerId = savedCustomer.getId();

        // Prepare updated customer data
        Customer updatedCustomer = new Customer(customerId, "David Updated", "david.updated@example.com");

        // Act
        restTemplate.put(BASE_URL + "/" + customerId, updatedCustomer);

        // Assert: Verify the update was persisted
        Customer dbCustomer = customerRepository.findById(customerId).orElse(null);
        assertNotNull(dbCustomer);
        assertEquals("David Updated", dbCustomer.getName());
        assertEquals("david.updated@example.com", dbCustomer.getEmail());
    }

    /**
     * Test: PUT /api/customer/{id} - Update non-existent customer
     * Verifies that 404 NOT_FOUND is returned when attempting to update a non-existent customer.
     */
    @Test
    void testUpdateNonExistentCustomer() {
        Customer customer = new Customer(null, "Non Existent", "nonexistent@example.com");

        // Act - Exception mapper returns 404 NOT_FOUND
        ResponseEntity<Customer> response = restTemplate.exchange(
                BASE_URL + "/999",
                org.springframework.http.HttpMethod.PUT,
                new org.springframework.http.HttpEntity<>(customer),
                Customer.class);

        // Assert: Verify 404 is returned and no customer was created
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(0, customerRepository.count());
    }

    /**
     * Test: DELETE /api/customer/{id} - Delete an existing customer
     * Verifies that a customer is deleted with HTTP 204 status.
     */
    @Test
    void testDeleteCustomer() {
        // Arrange: Create a test customer
        Customer customer = new Customer(null, "Edward Doe", "edward.doe@example.com");
        Customer savedCustomer = customerRepository.save(customer);
        Long customerId = savedCustomer.getId();

        // Verify customer exists before deletion
        assertTrue(customerRepository.findById(customerId).isPresent());

        // Act
        restTemplate.delete(BASE_URL + "/" + customerId);

        // Assert: Verify the customer was deleted
        assertFalse(customerRepository.findById(customerId).isPresent());
    }

    /**
     * Test: DELETE /api/customer/{id} - Delete non-existent customer
     * Verifies that HTTP 404 is returned when attempting to delete a non-existent customer.
     */
    @Test
    void testDeleteNonExistentCustomer() {
        // Act
        ResponseEntity<Void> response = restTemplate.exchange(
                BASE_URL + "/999",
                org.springframework.http.HttpMethod.DELETE,
                null,
                Void.class);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Test: Complete CRUD workflow
     * Verifies that all CRUD operations work together in a realistic scenario.
     */
    @Test
    void testCompleteCRUDWorkflow() {
        // Create a customer
        Customer newCustomer = new Customer(null, "Grace Doe", "grace.doe@example.com");
        ResponseEntity<Customer> createResponse = restTemplate.postForEntity(
                BASE_URL, newCustomer, Customer.class);
        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());

        Long customerId = createResponse.getBody().getId();

        // Verify customer was created
        ResponseEntity<Customer> getResponse = restTemplate.getForEntity(
                BASE_URL + "/" + customerId, Customer.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertEquals("Grace Doe", getResponse.getBody().getName());

        // Update the customer
        Customer updatedCustomer = new Customer(customerId, "Grace Updated", "grace.updated@example.com");
        restTemplate.put(BASE_URL + "/" + customerId, updatedCustomer);

        // Verify customer was updated
        Customer dbCustomer = customerRepository.findById(customerId).orElse(null);
        assertNotNull(dbCustomer);
        assertEquals("Grace Updated", dbCustomer.getName());

        // Delete the customer
        restTemplate.delete(BASE_URL + "/" + customerId);

        // Verify customer was deleted
        assertFalse(customerRepository.findById(customerId).isPresent());
    }
}
