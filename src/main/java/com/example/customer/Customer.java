package com.example.customer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Represents a customer entity with basic contact information.
 *
 * <p>This class is a domain model that encapsulates customer data including
 * unique identifier, name, and email address. It can be used as a data transfer
 * object (DTO) in REST API endpoints or as a persistent entity in database operations.</p>
 *
 * @author Neven C (nevenc)
 * @version 1.0
 * @since 2025
 */
@Entity
public class Customer {

    /** The unique identifier for the customer. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The customer's full name. */
    private String name;

    /** The customer's email address. */
    private String email;

    /**
     * Constructs an empty Customer instance.
     * Used primarily for reflection-based instantiation by frameworks.
     */
    public Customer() {
    }

    /**
     * Constructs a Customer with the specified attributes.
     *
     * @param id the unique customer identifier
     * @param name the customer's full name
     * @param email the customer's email address
     */
    public Customer(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * Returns the unique identifier of this customer.
     *
     * @return the customer ID, or null if not set
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for this customer.
     *
     * @param id the customer ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the full name of this customer.
     *
     * @return the customer's name, or null if not set
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the full name for this customer.
     *
     * @param name the customer's name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the email address of this customer.
     *
     * @return the customer's email address, or null if not set
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address for this customer.
     *
     * @param email the customer's email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
