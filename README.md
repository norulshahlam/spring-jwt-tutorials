# Spring Security with JWT 

### Introduction
The Bookstore project is an API service that categorize all the books in this store. It provides a top-of-the-line method to access the book information.

### Features
This tutorial provides the following features:

- Create a user with roles
- Remove roles
- Persist user data in DB
- Generate JWT and validate it

### Technology Stack
The Bookstore API is built using the following technology stack:

- Java 17
- Spring Boot 3.2.2
- Maven
- Docker (for PostgreSQL)
- H2 Database (for local development environment)

### Running your DB instance
docker run --name postgres-tutorial -e POSTGRES_PASSWORD=password -d -p 5432:5432 postgres
