# Web Quiz Engine

A RESTful web service for creating, managing, and solving quizzes with user authentication and progress tracking.

## Features

### Quiz Management
- **Create Quizzes** - Build custom quizzes with multiple-choice questions
- **Update Quizzes** - Modify existing quiz content
- **Delete Quizzes** - Remove quizzes you've created
- **Browse Quizzes** - View all available quizzes with pagination support

### User System
- **User Registration** - Create an account with email and password
- **Authentication** - Secure access using HTTP Basic Authentication
- **Authorization** - Quiz creators have exclusive edit/delete permissions

### Quiz Solving
- **Answer Quizzes** - Submit answers and receive instant feedback
- **Completion History** - Track your solved quizzes with timestamps
- **Pagination** - Navigate through quiz lists and completion history efficiently

## Technology Stack
- **Backend**: Spring Boot, Spring Security, Spring Data JPA
- **Security**: Spring Security with HTTP Basic Authentication
- **Database**: H2 Database (embedded)
- **Java Version**: 21
- **Build Tool**: Gradle

## API Endpoints

### User Operations
- `POST /api/register` - Register a new user
  
### Quiz Operations
- `POST /api/quizzes` - Create a new quiz (authenticated)
- `GET /api/quizzes` - Get all quizzes with pagination
- `GET /api/quizzes/{id}` - Get a specific quiz
- `PUT /api/quizzes/{id}` - Update a quiz (owner only)
- `DELETE /api/quizzes/{id}` - Delete a quiz (owner only)

### Solving Operations
- `POST /api/quizzes/{id}/solve` - Submit an answer
- `GET /api/quizzes/completed` - Get completion history with pagination

## Getting Started

### Prerequisites
- Java 21 or higher
- Gradle (or use included wrapper)

### Running the Application

```bash
./gradlew bootRun
```
The application will start on `http://localhost:8889`

### Building the Application

```shell script
./gradlew build
```

## Usage Example

1. **Register a user**
2. **Authenticate** using your credentials
3. **Create a quiz** with questions and correct answers
4. **Solve quizzes** and track your progress
5. **Manage your quizzes** (edit or delete)

## Security

- All endpoints (except registration) require authentication
- Passwords are securely encrypted
- Users can only modify/delete their own quizzes

## License

This project is part of a learning exercise.