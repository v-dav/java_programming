
# Fitness Tracker API

A comprehensive REST API for fitness data management with developer authentication, applicationEntity registration, and secure data endpoints. Built with Spring Boot 3, Spring Security, and Spring Data JPA.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [Getting Started](#getting-started)
- [Authentication](#authentication)
- [API Endpoints](#api-endpoints)
- [Rate Limiting](#rate-limiting)
- [Security](#security)
- [Data Models](#data-models)
- [Configuration](#configuration)
- [Development](#development)

## Overview

The Fitness Tracker API provides a secure platform for developers to register applications and manage fitness data through RESTful endpoints. It implements a multi-layered security approach with developer authentication, applicationEntity registration, and API key-based access control.

## Features

### Core Functionality
- **Developer Registration**: Secure signup process for API access
- **Application Management**: Register and manage applications with categorization
- **Fitness Data Endpoints**: CRUD operations for fitness tracking data
- **Multi-layered Authentication**: Support for both basic authentication and API key authentication

### Security & Performance
- **Spring Security Integration**: Comprehensive security configuration with custom authentication providers
- **Rate Limiting**: Token bucket algorithm implementation for API throttling
- **Stateless Session Management**: JWT-style authentication without server-side sessions
- **Input Validation**: Bean validation for all request payloads

### Technical Features
- **H2 Database**: In-memory database for development and testing
- **JPA/Hibernate**: Object-relational mapping with Spring Data JPA
- **RESTful Design**: Clean REST API following HTTP standards
- **Actuator Endpoints**: Health checks and monitoring capabilities

Based on your updated project structure, I can see you've reorganized the code into feature-based packages. Here's the updated Architecture section for your README:

## Architecture

The application follows a **feature-based package architecture** combined with layered architecture principles:

### Package Structure
```
src/main/java/fitnesstracker/
├── applications/           # Application management feature
│   ├── ApplicationEntity
│   ├── ApplicationRepository
│   ├── ApplicationService
│   ├── ApplicationRestController
│   └── DTOs (ApplicationDto, ApplicationRegistrationRequest, etc.)
├── fitness_data/          # Fitness data tracking feature  
│   ├── FitnessDataEntity
│   ├── FitnessDataRepository
│   ├── FitnessDataService
│   ├── FitnessDataRestController
│   └── DTOs (FitnessDataDto, UploadDataRequest)
├── users/                 # User management feature
│   ├── UserEntity
│   ├── UserRepository
│   ├── UserService
│   ├── UserRestController
│   ├── UserDetailServiceCustom
│   └── DTOs (UserDto, SignupRequest)
├── common/                # Shared components
│   ├── BaseEntity
│   └── Mapper
└── config/                # Security and application configuration
    ├── SecurityConfig
    ├── RateLimiterService
    └── Authentication components
```

### Architectural Layers

Within each feature package, the application follows a layered architecture:

```
┌─────────────────────────────────────────┐
│           Controller Layer              │
│        (REST endpoints)                 │
│  UserRestController                     │
│  ApplicationRestController              │
│  FitnessDataRestController              │
└─────────────────────────────────────────┘
                    │
┌─────────────────────────────────────────┐
│            Service Layer                │
│         (Business logic)                │
│  UserService                            │
│  ApplicationService                     │
│  FitnessDataService                     │
└─────────────────────────────────────────┘
                    │
┌─────────────────────────────────────────┐
│          Repository Layer               │
│          (Data access)                  │
│  UserRepository                         │
│  ApplicationRepository                  │
│  FitnessDataRepository                  │
└─────────────────────────────────────────┘
                    │
┌─────────────────────────────────────────┐
│         Security Layer                  │
│   (Authentication & Authorization)      │
│  UserDetailServiceCustom                │
│  AccessTokenAuthenticationProvider      │
│  RateLimiterService                     │
└─────────────────────────────────────────┘
                    │
┌─────────────────────────────────────────┐
│       Configuration Layer               │
│      (Spring configuration)             │
│  SecurityConfig                         │
│  Database configuration                 │
└─────────────────────────────────────────┘
```


### Design Principles

- **Feature-Based Organization**: Code is organized by business features rather than technical layers
- **Domain-Driven Design**: Each package represents a distinct domain area
- **Separation of Concerns**: Clear separation between presentation, business logic, and data layers
- **Single Responsibility**: Each class has a focused, well-defined responsibility
- **Dependency Injection**: Loose coupling through Spring's IoC container

This architecture provides better maintainability, testability, and scalability by organizing code around business capabilities while maintaining clear separation of technical concerns.

## Getting Started

### Prerequisites
- Java 17 or higher
- Gradle 7+

### Installation

1. Clone the repository:
```
bash
git clone <repository-url>
cd fitness_tracker_api
```
2. Build the project:
```
bash
./gradlew build
```
3. Run the applicationEntity:
```
bash
./gradlew bootRun
```
The API will be available at `http://localhost:8080`

### Database Access
- H2 Console: `http://localhost:8080/h2-console`
- Database URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: `sa`

## Authentication

The API supports two authentication methods:

### 1. Developer Authentication (Basic Auth)
Used for developer signup and applicationEntity management.

### 2. Application API Key Authentication
Used for fitness data operations. Applications receive API keys upon registration.

## API Endpoints

### Developer Management
```
POST /api/developers/signup       # Register a new developer account.
GET /api/developers/{id}          # Get developer details
```

### Application Management
```
POST   /api/applications/register # Register new applicationEntity

```

### Fitness Data
```
GET    /api/tracker               # Retrieve fitness data of an application
POST   /api/tracker               # Upload fitness data by an application
```


### Monitoring
```
GET /actuator/health              # Health check
GET /actuator/info                # Application info
```


## Rate Limiting

The API implements rate limiting for applications with "basic" profile using a token bucket algorithm:

- **Algorithm**: Token bucket with configurable capacity and refill rate
- **Scope**: Per API key
- **Default Limits**: 1 request per second per API key
- **Implementation**: In-memory concurrent hash map for high performance

Rate limits are applied to /api/tracker endpoints and to "basic" applications. Premium applications do not have any rate limit

## Security

### Security Configuration
- **CSRF Protection**: Disabled for API usage
- **Session Management**: Stateless (no server-side sessions)
- **CORS**: Configurable for cross-origin requests
- **H2 Console**: Enabled for development (frame options set to same origin)

### Authentication Flow
1. Developers register using basic authentication
2. Applications are registered and receive API keys
3. Fitness data endpoints require valid API key authentication (X-Api-Key header)
4. Rate limiting is applied per API key

### Protected Endpoints
- All `/api/developers/**` endpoints (except signup)
- All `/api/applications/**` endpoints
- All `/api/tracker/**` endpoints

## Configuration

### Application Properties
Key configuration options available in `application.properties`:

- **Server Port**: Default 8080
- **Database Configuration**: H2 in-memory database
- **JPA Settings**: Hibernate DDL auto-generation
- **Security Settings**: Password encoding and session management

## Development

### Key Technologies
- **Spring Boot 3.2.0**: Main framework
- **Spring Security**: Authentication and authorization
- **Spring Data JPA**: Data persistence
- **Bean Validation**: Input validation
- **H2 Database**: In-memory database
- **Gradle**: Build tool


### Code Quality
The project follows:
- Clean Code principles
- SOLID design principles
- RESTful API design standards
- Spring Boot best practices

## Contributing

Fork the repository and play around

## License

This project is licensed under the MIT License

