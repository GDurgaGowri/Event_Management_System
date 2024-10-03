# University Event Management System

## Overview

The **University Event Management System** is a comprehensive application designed to manage university events efficiently. It provides functionalities for creating, updating, and deleting events, as well as managing locations and organizers. The system leverages modern technologies and frameworks to ensure a scalable, secure, and user-friendly experience.

## Features

- **CRUD Operations:** Perform create, read, update, and delete operations on Events, Organizers, and Locations.
- **GraphQL API:** Flexible querying and mutation capabilities using GraphQL.
- **REST API:** Standard RESTful endpoints for integration.
- **Spring Security:** Secure API access with role-based authorization.
- **AOP Logging:** Aspect-Oriented Programming (AOP) for centralized logging of key actions.
- **Liquibase Integration:** Database version control and migration management.

## Tech Stack

- **Languages:** Kotlin, Java, SQL
- **Frameworks:** Spring Boot, Spring Data JPA, Spring Security, Hibernate
- **Web Technologies:** REST APIs, GraphQL, Servlets, JDBC
- **Database Management:** MySQL, Liquibase for version control
- **Tools:** Git, IntelliJ IDEA, Eclipse

## Project Structure

```
src
├── main
│   ├── kotlin
│   │   └── com.example.eventManagement
│   │       ├── Entity         # Contains the JPA entities
│   │       ├── Repository     # Spring Data JPA repositories
│   │       ├── Service        # Business logic and services
│   │       ├── Controller     # REST API controllers
│   │       ├── Resolver       # GraphQL resolvers
│   │       └── Aspect         # AOP logging aspects
│   └── resources
│       ├── application.properties   # Spring Boot configuration
│       ├── db
│       │   └── changelog            # Liquibase changelogs
│       └── graphql
│           └── schema.graphqls      # GraphQL schema definitions
└── test
    └── kotlin
        └── com.example.eventManagement # Unit and integration tests
```
## API Endpoints

### REST Endpoints

- `GET /events`: Retrieve all events.
- `GET /events/{id}`: Retrieve an event by ID.
- `POST /events`: Create a new event.
- `PUT /events/{id}`: Update an existing event.
- `DELETE /events/{id}`: Delete an event by ID.

### GraphQL Queries & Mutations

- `allEvents`: Retrieve all events.
- `eventById(id: ID!)`: Retrieve an event by ID.
- `createEvent(name: String!, date: String!, locationId: ID!, organizerId: ID!)`: Create a new event.
- `updateEvent(id: ID!, name: String, date: String, locationId: ID, organizerId: ID)`: Update an existing event.
- `deleteEvent(id: ID!)`: Delete an event by ID.
