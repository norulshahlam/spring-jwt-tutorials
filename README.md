# Spring Security with JWT 

### Introduction
This is a tutorial on how to generate, validate and refresh JWT using Spring Security. And based on the roles, they can only access certain endpoints.

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


### Structure  
A user will have basic details like name, username, id, password.  
A user can >1 roles.

|id|email|name|password|
|--|-----|----|--------|
|1|admin@gmail.com|admin|$2a$10$vMqNLzM5sT6gIR5VigDj.uypvg1jwteA2CEr8z0D8qRhZzTTnFm6m|
|2|applicant@gmail.com|applicant|$2a$10$LKR5t68W4QC.NrlGuIhI6ubP.xSE87b0u1d02QhKFYg7SikTc6dXe|
|3|assessor@gmail.com|assessor|$2a$10$GaUlmux3yXuDvX1dEWUXUenyqAAKFD1tX7xQcNUBcVMzo6jSnPGTC|
|4|approver@gmail.com|approver|$2a$10$53enROX024K5ZiAbcrX4aOG6ehDGip.Vrm5BqPf5KC2gKJkaeSUT2|


Roles:

|id|role_name|
|--|---------|
|1|ADMIN|
|2|APPLICANT|
|3|ASSESSOR|
|4|APPROVER|


Mapping:

|user_registration_id|roles_id|
|--------------------|--------|
|1                   |1       |
|1                   |4       |
|2                   |2       |
|3                   |3       |
|4                   |4       |

### Running your DB instance
    docker run --name postgres-tutorial -e POSTGRES_PASSWORD=password -d -p 5432:5432 postgres

### Test DB

<details>
<summary>Click to expand</summary><br>

Run docker DB in cli  

    docker exec -it postgres bash  

Connect to DB   

    psql -h localhost -U admin

List of databases

    \l

Connect to database

    \c postgres;

List of tables

    \d

Stop & remove all running proceses  

    docker rm $(docker ps -a -q) -f  
</details>

### Use Case

We have preloaded users with different roles for testing. Simply open a browser and hit `http://localhost:8082/swagger-ui/index.html#/` to access swagger API.

- Login to retrieve JWT.
- Use JWT and paste into Authorization input field.
- Hit any endpoint to test your role


### Useful resources

<details>
<summary>Click to expand</summary><br>

Spring Auth using JPA

    https://www.youtube.com/watch?v=awcCiqBO36E&t=1588s

Epoch & Unix Timestamp Conversion Tools

    https://www.epochconverter.com/
</details>
