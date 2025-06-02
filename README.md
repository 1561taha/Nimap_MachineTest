# Products and Categories API

This project is a Spring Boot application that provides a RESTful API for managing products and categories.

## Prerequisites

*   Spring Boot `3.4.5`
*   Java 21 or higher (as specified in `pom.xml` - `<java.version>21</java.version>`)
*   Apache Maven
*   MySQL Server (or update `application.properties` for a different RDB)

## Project Structure

The project follows a standard Maven project structure:

```
Products/
├── .mvn/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/taha/Products/
│   │   │       ├── Controller/     # REST API controllers (CategoryController, ProductController)
│   │   │       ├── Model/          # JPA Entities (Category, Product) and DTOs (CategoryDto, ProductResponseDto)
│   │   │       ├── Repo/           # Spring Data JPA repositories (CategoryRepo, ProductRepo)
│   │   │       ├── Service/        # Business logic services (CategoryService, ProductService)
│   │   │       └── ProductsApplication.java  # Main Spring Boot application class
│   │   └── resources/
│   │       ├── application.properties  # Application and database configuration
│   │       ├── static/               # Static resources (if any)
│   │       └── templates/            # Server-side templates (if any)
│   └── test/
│       └── java/                   # Unit and integration tests
├── pom.xml                         # Maven project configuration
└── README.md                       # This file
```

## Setup and Installation

1.  **Clone the repository (if applicable) or ensure you have the project files.**
2.  **Database Setup:**
    *   Make sure you have MySQL server running.
    *   Create a database named `CatProd` (or the name specified in `spring.datasource.url` in `application.properties`).
    *   The application is configured to connect to `jdbc:mysql://localhost:3306/CatProd` with username `root` and password `root`. Update `src/main/resources/application.properties` if your MySQL configuration is different.
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/CatProd?createDatabaseIfNotExist=true
    spring.datasource.username=root
    spring.datasource.password=root
    spring.jpa.hibernate.ddl-auto=update # Creates/updates schema automatically
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
    ```
3.  **Build the project using Maven:**
    Open a terminal in the project root directory and run:
    ```bash
    mvn clean install
    ```

## Running the Application

After a successful build, you can run the application using Maven:

```bash
mvn spring-boot:run
```

Alternatively, you can run the packaged JAR file (after `mvn clean install`):

```bash
java -jar target/Products-0.0.1-SNAPSHOT.jar
```

The application will start on `http://localhost:8084` by default (as specified in `application.properties` - `server.port=8084`).

## Key Features

*   **Spring Boot:** Utilizes Spring Boot for rapid application development.
*   **REST Controllers:** Exposes API endpoints using Spring MVC.
*   **JPA & Hibernate:** Uses Spring Data JPA with Hibernate for database interaction.
*   **RDB Configuration:** Configured for MySQL database.
*   **Annotation-Based Configuration:** Leverages annotations for Spring configuration.
*   **CRUD Operations:** Provides full Create, Read, Update, and Delete operations for Categories and Products.
*   **One-to-Many Relationship:** Implements a one-to-many relationship between Categories and Products (one category can have multiple products).
*   **Server-Side Pagination:** API endpoints for fetching all categories and products support pagination.
*   **Product Details with Category:** Fetching a single product returns a ProductResponseDto that includes its associated CategoryDto.
*   **Data Transfer Objects:** All API responses use DTOs instead of exposing entity objects directly.

## API Endpoints

All endpoints are prefixed with `/api`.

### Category APIs

Base URL: `http://localhost:8084/api/categories`

| Method | Endpoint          | Description                                   | Request Body | Response Body      |
|--------|-------------------|-----------------------------------------------|--------------|--------------------|  
| GET    | `?page={page_num}`| Get all categories (paginated, e.g., `?page=0`) | None         | Page of CategoryDto |
| POST   | `/`               | Create a new category                         | Category JSON| CategoryDto        |
| GET    | `/{id}`           | Get a category by its ID                      | None         | CategoryDto        |
| PUT    | `/{id}`           | Update an existing category by its ID         | Category JSON| CategoryDto        |
| DELETE | `/{id}`           | Delete a category by its ID                   | None         | None (200 OK)      |

**Category JSON Example:**
```json
{
  "name": "Electronics",
  "description": "Gadgets and electronic devices"
}
```

### Product APIs

Base URL: `http://localhost:8084/api/products`

| Method | Endpoint          | Description                                  | Request Body | Response Body                     |
|--------|-------------------|----------------------------------------------|--------------|-----------------------------------|
| GET    | `?page={page_num}`| Get all products (paginated, e.g., `?page=0`)  | None         | Page of ProductResponseDto        |
| POST   | `/`               | Create a new product                         | Product JSON | ProductResponseDto                |
| GET    | `/{id}`           | Get a product by its ID (includes category)  | None         | ProductResponseDto (with CategoryDto)|
| PUT    | `/{id}`           | Update an existing product by its ID         | Product JSON | ProductResponseDto                |
| DELETE | `/{id}`           | Delete a product by its ID                   | None         | None (200 OK)                     |

**Product JSON Example (for POST/PUT):**
```json
{
  "name": "Laptop X1",
  "description": "High-performance laptop",
  "price": 1200.00,
  "category": {
    "id": 1 // Assuming a category with ID 1 exists
  }
}
```

**ProductResponseDto JSON Example (for GET by ID):**
```json
{
  "id": 101,
  "name": "Laptop X1",
  "price": 1200.00,
  "category": {
    "id": 1,
    "name": "Electronics",
    "description": "Gadgets and electronic devices"
  }
}
```

## Database Configuration

Database settings are in `src/main/resources/application.properties`.

*   **URL:** `spring.datasource.url=jdbc:mysql://localhost:3306/CatProd?createDatabaseIfNotExist=true`
*   **Username:** `spring.datasource.username=root`
*   **Password:** `spring.datasource.password=root`
*   **DDL Auto:** `spring.jpa.hibernate.ddl-auto=update` (Hibernate will attempt to update the schema based on entities. For production, consider `validate` or manual schema management.)
*   **Show SQL:** `spring.jpa.show-sql=true` (Logs Hibernate-generated SQL queries to the console, useful for debugging.)