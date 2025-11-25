package com.example.customer;


import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing Customer entities.
 *
 * <p>This interface provides data access operations for Customer entities using Spring Data JPA.
 * It extends JpaRepository to inherit standard CRUD operations without requiring explicit implementation.
 * </p>
 *
 * <p>Available operations (inherited from JpaRepository):</p>
 * <ul>
 *   <li><strong>Create:</strong> save(Customer), saveAll(Iterable&lt;Customer&gt;)</li>
 *   <li><strong>Read:</strong> findById(Long), findAll(), findAllById(Iterable&lt;Long&gt;)</li>
 *   <li><strong>Update:</strong> save(Customer), saveAndFlush(Customer)</li>
 *   <li><strong>Delete:</strong> deleteById(Long), delete(Customer), deleteAll()</li>
 *   <li><strong>Utility:</strong> existsById(Long), count()</li>
 * </ul>
 *
 * <p>This is a Spring Data repository that automatically handles database transactions
 * and provides a clean abstraction layer for customer data persistence.</p>
 *
 * @author Neven C (nevenc)
 * @version 1.0
 * @since 2025
 * @see Customer
 * @see org.springframework.data.jpa.repository.JpaRepository
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
