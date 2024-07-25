# Book Management System

## Overview

The Book Management System is a simple application for managing books. It provides RESTful APIs for creating, reading, updating, and deleting books. The application is built with Spring Boot, Spring Data JPA, and H2 database.

## Features

- Add a new book
- Retrieve a book by its ID
- Retrieve all books
- Update a book
- Delete a book

## Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- H2 Database
- Maven
- JUnit 5
- MockMvc

## Getting Started

### Prerequisites

- JDK 11 or higher
- Maven 3.6.3 or higher

### Clone the Repository

```bash
git clone https://github.com/yourusername/book-management.git
cd book-management
```

### Build the project

```bash
./mvnw clean install
```

### Running the Application

```bash
./mvnw spring-boot:run
```

The application will start and be accessible at http://localhost:8080.

### Running Tests

Tests divide into 2 categories

- Unit Test
- Integration Test

To run unit test

```bash
./mvnw test -P unit-test
```

To run integration test

```bash
./mvnw test -P integration-test
```

To run both test

```bash
./mvnw test
```

## Directory Structure

```
book-management/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com
│   │   │       └── bookmanagement/bookmanagement
│   │   │               ├── BookmanagementApplication.java
│   │   │               ├── configuration
│   │   │               │   └── JpaConfig.java
│   │   │               ├── constant
│   │   │               │   └── BookManagementConstant.java
│   │   │               ├── controller
│   │   │               │   └── BookController.java
│   │   │               ├── exception
│   │   │               │   └── GlobalExceptionHandler.java
│   │   │               ├── model
│   │   │               │   └── Book.java
│   │   │               ├── repository
│   │   │               │   └── BookRepository.java
│   │   │               └── service
│   │   │                   └── BookService.java
│   │   └── resources
│   │       ├── application-integrationtest.properties
│   │       └── application.properties
│   └── test
│       └── java
│           └── com
│               └── bookmanagement
│                   └── bookmanagement
│                       ├── controller
│                       │   └── BookControllerTest.java
│                       ├── integration
│                       │   └── BookControllerIntegrationTest.java
│                       ├── repository
│                       │   └── BookRepositoryTest.java
│                       └── service
│                           └── BookServiceTest.java
├── .gitignore
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```
