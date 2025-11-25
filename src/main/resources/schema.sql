-- ============================================================================
-- Customer Table Schema
-- ============================================================================
-- This SQL script initializes the customer table for the Jersey Demo application.
-- It is automatically executed on application startup due to the configuration:
-- spring.sql.init.mode=always in application.properties

-- Drop the table if it exists (useful for development/testing)
DROP TABLE IF EXISTS customer CASCADE;

-- Create the customer table
CREATE TABLE customer (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

-- Create an index on email for faster lookups
CREATE INDEX idx_customer_email ON customer(email);

-- ============================================================================
-- Sample Data (Optional - for testing and development)
-- ============================================================================

-- Insert sample customers for testing
INSERT INTO customer (name, email) VALUES
    ('Jack Doe', 'jack.doe@example.com'),
    ('Jane Doe', 'jane.doe@example.com'),
    ('John Doe', 'john.doe@example.com'),
    ('Jean Doe', 'jean.doe@example.com'),
    ('Jose Doe', 'jose.doe@example.com'),
    ('Jess Doe', 'jess.doe@example.com'),
    ('Josh Doe', 'josh.doe@example.com'),
    ('Judy Doe', 'judy.doe@example.com'),
    ('Juan Doe', 'juan.doe@example.com'),
    ('Jill Doe', 'jill.doe@example.com');

-- Display the inserted data (useful for verification in some tools)
-- Note: This SELECT statement helps verify the data was inserted
-- SELECT * FROM customer;