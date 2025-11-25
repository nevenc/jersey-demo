# jersey-demo

An example Spring Boot with JAX-RS (Jersey) REST API endpoints.

## Project Overview

This project showcases:
- **Spring Boot 2.7.18** with Jersey 2.35 framework (JAX-RS 2.1)
- **RESTful API** for customer CRUD operations
- **Spring Data JPA** for database persistence
- **PostgreSQL** as the backend database

## Technology Stack
- **Java 8**
- **Spring Boot 2.7.18** - Application framework
- **Jersey 2.35** (via Spring Boot Starter Jersey) - JAX-RS 2.1 implementation
- **Spring Data JPA** - Data persistence
- **PostgreSQL 18.1** - Database
- **Maven** - Build tool
- **Docker Compose** - Container orchestration
- **JUnit 5** - Testing framework
- **TestRestTemplate** - Spring HTTP client for integration testing

## Project Structure

```
jersey-demo/
├── src/main/java/com/example/
│   ├── config/
│   │   ├── JerseyConfiguration.java             # JAX-RS configuration and registration
│   │   └── ErrorResponse.java                   # Standard error response DTO
│   └── customer/
│       ├── Customer.java                        # JPA Entity with @Entity annotation
│       ├── CustomerResource.java                # REST endpoints with @Produces/@Consumes
│       ├── CustomerRepository.java              # Spring Data JPA repository
│       ├── CustomerNotFoundException.java       # Custom exception for missing customers
│       └── CustomerNotFoundExceptionMapper.java # JAX-RS ExceptionMapper provider
├── src/main/resources/
│   ├── application.properties                   # Spring Boot configuration
│   └── schema.sql                               # Database initialization script
├── src/test/java/com/example/
│   ├── JerseyDemoApplicationTests.java          # Application context test
│   └── customer/
│       └── CustomerResourceTests.java           # REST endpoint integration tests
├── pom.xml                                      # Maven dependencies
├── compose.yaml                                 # Docker Compose configuration
├── LICENSE                                      # Apache License 2.0
└── README.md                                    # This file
```

## Quick Start

### Prerequisites
- Java 8 or higher
- Maven 3.6+
- Docker & Docker Compose (for running PostgreSQL)

## Run with H2 (in-memory) database

### 1. Build the Application

```bash
./mvnw clean package
```

### 2. Run the Application

```bash
./mvnw spring-boot:run
```

Or run the JAR file:

```bash
java -jar target/jersey-demo-1.0.0.jar
```

The application will start on `http://localhost:8080`

## Run with PostgreSQL database

### 1. Start PostgreSQL Database

Using Docker Compose:

```bash
docker-compose up -d
```

This starts a PostgreSQL 18.1 container with:
- Database: `mydatabase`
- Username: `myuser`
- Password: `secret`
- Port: `5432`

### 2. Build the Application

```bash
./mvnw clean package
```

### 3. Run the Application

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=postgres
```

Or run the JAR file:

```bash
java -jar target/jersey-demo-1.0.0.jar -Dspring.profiles.active=postgres
```

The application will start on `http://localhost:8080`


## API Documentation

All endpoints are prefixed with `/api/customer`

### Base URL
```
http://localhost:8080/api/customer
```

### Endpoints

#### 1. Get All Customers
```
GET /api/customer
```

**Description:** Retrieves all customers from the database

**Response:**
```json
[
  {
    "id": 1,
    "name": "Jack Doe",
    "email": "jack.doe@example.com"
  },
  {
    "id": 2,
    "name": "Jane Doe",
    "email": "jane.doe@example.com"
  }
  ...
]
```

**Status Code:** `200 OK`

---

#### 2. Get Customer by ID
```
GET /api/customer/{id}
```

**Description:** Retrieves a specific customer by their ID

**Path Parameters:**
- `id` (Long) - The customer ID

**Example Request:**
```
GET /api/customer/1
```

**Response:**
```json
{
  "id": 1,
  "name": "Jack Doe",
  "email": "jack.doe@example.com"
}
```

**Status Codes:**
- `200 OK` - Customer found
- `404 Not Found` - Customer not found

**Error Response (404):**
```json
{
  "status": 404,
  "message": "Customer with id 999 not found",
  "timestamp": "2025-11-20T10:30:45.123456"
}
```

---

#### 3. Create Customer
```
POST /api/customer
```

**Description:** Creates a new customer

**Request Body:**
```json
{
  "name": "Alice Johnson",
  "email": "alice@example.com"
}
```

**Response:**
```json
{
  "id": 11,
  "name": "Alice Johnson",
  "email": "alice@example.com"
}
```

**Status Code:** `201 Created`

---

#### 4. Update Customer
```
PUT /api/customer/{id}
```

**Description:** Updates an existing customer by ID

**Path Parameters:**
- `id` (Long) - The customer ID

**Request Body:**
```json
{
  "name": "Alice Johnson Updated",
  "email": "alice.updated@example.com"
}
```

**Response:**
```json
{
  "id": 11,
  "name": "Alice Johnson Updated",
  "email": "alice.updated@example.com"
}
```

**Status Codes:**
- `200 OK` - Customer updated successfully
- `404 Not Found` - Customer not found

**Error Response (404):**
```json
{
  "status": 404,
  "message": "Customer with id 999 not found",
  "timestamp": "2025-11-20T10:30:45.123456"
}
```

---

#### 5. Delete Customer
```
DELETE /api/customer/{id}
```

**Description:** Deletes a customer by ID

**Path Parameters:**
- `id` (Long) - The customer ID

**Example Request:**
```
DELETE /api/customer/3
```

**Status Codes:**
- `204 No Content` - Customer deleted successfully
- `404 Not Found` - Customer not found

---

## Testing the Endpoints

### Using cURL

#### Get all customers:
```bash
curl -X GET http://localhost:8080/api/customer
```

#### Get customer by ID:
```bash
curl -X GET http://localhost:8080/api/customer/1
```

#### Create a new customer:
```bash
curl -X POST http://localhost:8080/api/customer \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Bob Wilson",
    "email": "bob@example.com"
  }'
```

#### Update a customer:
```bash
curl -X PUT http://localhost:8080/api/customer/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe Updated",
    "email": "john.doe.updated@example.com"
  }'
```

#### Delete a customer:
```bash
curl -X DELETE http://localhost:8080/api/customer/1
```

### Using HTTPie

HTTPie is a user-friendly command-line HTTP client that provides a simpler syntax than cURL. Install it with:

```bash
brew install httpie
```

Then use the following commands:

#### Get all customers:
```bash
http GET localhost:8080/api/customer
```

#### Get customer by ID:
```bash
http GET localhost:8080/api/customer/1
```

#### Create a new customer:
```bash
http POST localhost:8080/api/customer \
  name="David Lee" \
  email="david@example.com"
```

#### Update a customer:
```bash
http PUT localhost:8080/api/customer/1 \
  name="David Lee Updated" \
  email="david.updated@example.com"
```

#### Delete a customer:
```bash
http DELETE localhost:8080/api/customer/1
```

HTTPie automatically handles JSON formatting, headers, and makes the requests more readable and intuitive compared to cURL.

## Database Configuration

The application connects to PostgreSQL using the following properties (in `application-postgres.properties`):

```properties
spring.datasource.url=jdbc:postgresql://localhost/mydatabase
spring.datasource.username=myuser
spring.datasource.password=secret
```

**Note:** Ensure PostgreSQL is running before starting the application. Use Docker Compose for easy setup.

## Database Initialization

The application automatically initializes the database with a schema and sample data on startup.

### Schema and Sample Data (schema.sql)

The `schema.sql` file in `src/main/resources/` contains:
- **Customer table creation** with id (auto-increment), name, and email fields
- **Index on email** for faster lookups
- **10 sample customers** with names starting with 'J' and the last name 'Doe'

The script runs automatically when the application starts due to:
```properties
spring.sql.init.mode=always
```

To disable automatic initialization in production:
```properties
spring.sql.init.mode=never
```

### Sample Customer Data

The following customers are automatically inserted:
1. Jack Doe (jack.doe@example.com)
2. Jane Doe (jane.doe@example.com)
3. John Doe (john.doe@example.com)
4. Jean Doe (jean.doe@example.com)
5. Jose Doe (jose.doe@example.com)
6. Jess Doe (jess.doe@example.com)
7. Josh Doe (josh.doe@example.com)
8. Judy Doe (judy.doe@example.com)
9. Juan Doe (juan.doe@example.com)
10. Jill Doe (jill.doe@example.com)

## Testing

### Running Tests

Execute all tests with Maven:

```bash
./mvnw test
```

### Test Coverage

The project includes comprehensive integration tests using **TestRestTemplate** that verify all CRUD operations:

**Location:** `src/test/java/com/example/customer/CustomerResourceTests.java`

**Test Cases (11 tests):**
1. `testGetAllCustomers` - Verify GET endpoint returns all customers
2. `testGetAllCustomersEmpty` - Verify empty list when no customers exist
3. `testGetCustomerById` - Verify GET by ID returns correct customer
4. `testGetCustomerByIdNotFound` - Verify 404 NOT_FOUND for missing customer (via ExceptionMapper)
5. `testCreateCustomer` - Verify POST creates customer with 201 CREATED
6. `testCreateMultipleCustomers` - Verify multiple customer creation
7. `testUpdateCustomer` - Verify PUT updates customer data
8. `testUpdateNonExistentCustomer` - Verify 404 NOT_FOUND when updating missing customer (via ExceptionMapper)
9. `testDeleteCustomer` - Verify DELETE removes customer with 204 NO_CONTENT
10. `testDeleteNonExistentCustomer` - Verify 404 NOT_FOUND for missing customer
11. `testCompleteCRUDWorkflow` - Full integration test of all operations

### Example Test Run Output

```
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0 - in JerseyDemoApplicationTests
[INFO] Tests run: 11, Failures: 0, Errors: 0, Skipped: 0 - in CustomerResourceTests
[INFO] Tests run: 12, Failures: 0, Errors: 0
[INFO] BUILD SUCCESS
```

## Logging

The application uses SLF4J for logging. Customer operations are logged at INFO level:

```
INFO - Initializing CustomerResource.
INFO - Fetching all customers
INFO - Fetching customer with id: 1
INFO - Creating new customer: Alice Johnson
INFO - Updating customer with id: 1
INFO - Deleting customer with id: 1
```

## Docker Compose Commands

Start services:
```bash
docker-compose up -d
```

Stop services:
```bash
docker-compose down
```

View logs:
```bash
docker-compose logs -f postgres
```

## Code Documentation

All classes, methods, and fields include comprehensive JavaDoc comments:

**Core Components:**
- **Customer.java** - JPA entity documentation with field descriptions
- **CustomerResource.java** - REST endpoint documentation with descriptions
- **CustomerRepository.java** - Spring Data JPA repository interface documentation
- **JerseyConfiguration.java** - Jersey configuration and resource registration documentation

**Exception Handling:**
- **CustomerNotFoundException.java** - Custom exception documentation for missing customers
- **CustomerNotFoundExceptionMapper.java** - JAX-RS exception mapper provider documentation
- **ErrorResponse.java** - Standard error response DTO documentation

Generate HTML documentation with:
```bash
./mvnw javadoc:javadoc
```

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.

```
Copyright 2025 Neven C (nevenc)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

---
Copyright 2025 Neven C (nevenc). Licensed under Apache License 2.0.

