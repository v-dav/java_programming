# Feedback Service

A Spring Boot REST API service for managing customer feedback with rating system, built with MongoDB for data persistence.

## 🚀 Features

- **CRUD Operations**: Create, read, and list customer feedback
- **Rating System**: 1-5 star rating validation
- **Advanced Filtering**: Filter feedback by rating, customer, product, and vendor
- **Pagination**: Configurable page size and navigation
- **Input Validation**: Comprehensive request validation using Bean Validation
- **MongoDB Integration**: Uses MongoDB with Spring Data
- **Testcontainers Support**: Automated MongoDB container setup for development

## 🛠 Tech Stack

- **Java 17+**
- **Spring Boot 3.x**
- **Spring Data MongoDB**
- **MongoDB**
- **Jakarta Validation**
- **Testcontainers**
- **Jackson** (JSON processing)

## 📋 Prerequisites

- Java 17 or higher
- Gradle 7.0+
- Docker (for Testcontainers MongoDB setup)

## 🏃‍♂️ Quick Start

### 1. Clone the Repository
```bash
git clone <repository-url>
cd feedback-service
```

### 2. Run the Application
```bash
./gradlew bootRun
```

The application will automatically start a MongoDB container using Testcontainers and be available at `http://localhost:8080`.

### 3. Test the API
```bash
# Create feedback
curl -X POST http://localhost:8080/feedback \
  -H "Content-Type: application/json" \
  -d '{
    "rating": 5,
    "feedback": "Excellent product quality!",
    "customer": "john.doe@example.com",
    "product": "Widget Pro",
    "vendor": "TechCorp"
  }'

# Get all feedback
curl http://localhost:8080/feedback
```

## 📖 API Documentation

### Base URL
```
http://localhost:8080/feedback
```

### Endpoints

#### Create Feedback
```http
POST /feedback
Content-Type: application/json

{
  "rating": 5,                    // Required: 1-5
  "feedback": "Great product!",   // Optional
  "customer": "user@example.com", // Optional
  "product": "Product Name",      // Required
  "vendor": "Vendor Name"         // Required
}
```

**Response:**
- `201 Created` with `Location` header
- `400 Bad Request` for validation errors

#### Get Feedback by ID
```http
GET /feedback/{id}
```

**Response:**
```json
{
  "id": "507f1f77bcf86cd799439011",
  "rating": 5,
  "feedback": "Great product!",
  "customer": "user@example.com",
  "product": "Product Name",
  "vendor": "Vendor Name"
}
```

#### List All Feedback (with Pagination & Filtering)
```http
GET /feedback?page=1&perPage=10&rating=5&customer=user@example.com&product=Widget&vendor=TechCorp
```

**Query Parameters:**
- `page` (default: 1) - Page number (minimum: 1)
- `perPage` (default: 10) - Items per page (5-20)
- `rating` (optional) - Filter by rating (1-5)
- `customer` (optional) - Filter by customer
- `product` (optional) - Filter by product
- `vendor` (optional) - Filter by vendor

**Response:**
```json
{
  "total_documents": 25,
  "is_first_page": true,
  "is_last_page": false,
  "documents": [
    {
      "id": "507f1f77bcf86cd799439011",
      "rating": 5,
      "feedback": "Great product!",
      "customer": "user@example.com",
      "product": "Product Name",
      "vendor": "Vendor Name"
    }
  ]
}
```

## 🗄 Database Schema

### Feedback Collection
```javascript
{
  "_id": ObjectId("507f1f77bcf86cd799439011"),
  "rating": 5,                    // Integer 1-5
  "feedback": "Great product!",   // String (optional)
  "customer": "user@example.com", // String (optional)
  "product": "Product Name",      // String (required)
  "vendor": "Vendor Name"         // String (required)
}
```

## 🔧 Configuration

### Application Properties
Create `application.yml` or `application.properties` for custom configuration:

```
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=feedback_db

mongodb.image=mongo:5

```

### MongoDB Connection
The application uses Testcontainers by default, which automatically:
- Starts a MongoDB container on port 27017
- Creates the `feedback_db` database
- Handles container lifecycle

For production, configure your MongoDB connection in application properties.

## 🧪 Testing

### Run Tests
```bash
./gradlew test
```

### Additional Gradle Commands
```bash
# Clean and build
./gradlew clean build

# Run with specific profile
./gradlew bootRun --args='--spring.profiles.active=dev'

# Build JAR for production
./gradlew bootJar
```

### Manual Testing Examples
```bash
# Create multiple feedback entries
curl -X POST http://localhost:8080/feedback \
  -H "Content-Type: application/json" \
  -d '{"rating": 4, "feedback": "Good quality", "product": "Widget", "vendor": "ACME"}'

# Filter by rating
curl "http://localhost:8080/feedback?rating=5"

# Filter by vendor with pagination
curl "http://localhost:8080/feedback?vendor=ACME&page=1&perPage=5"
```

## 📁 Project Structure

```
feedback-service/
├── gradle/                       # Gradle wrapper files
├── src/main/java/feedbackservice/
│   ├── FeedbackApplication.java      # Main application class
│   ├── Controller.java               # REST endpoints
│   ├── FeedbackService.java          # Business logic
│   ├── FeedbackRepository.java       # Data access layer
│   ├── FeedbackEntity.java           # MongoDB entity
│   ├── FeedbackDTO.java              # Data transfer object
│   ├── FeedbackRequest.java          # Request validation
│   ├── Mapper.java                   # Entity/DTO mapping
│   └── MongoContainerProvider.java   # Testcontainers setup
├── build.gradle                  # Build configuration
├── gradlew                      # Gradle wrapper (Unix)
├── gradlew.bat                  # Gradle wrapper (Windows)
└── settings.gradle              # Project settings
```

## 🔍 Development Notes

### Validation Rules
- **Rating**: Required, must be between 1-5
- **Product**: Required, cannot be blank
- **Vendor**: Required, cannot be blank
- **Feedback**: Optional text field
- **Customer**: Optional text field

### Pagination Behavior
- Page numbers start from 1
- Invalid page numbers default to 1
- Per-page limits: minimum 5, maximum 20, default 10
- Results are sorted by ID in descending order (newest first)

### Error Handling
- `400 Bad Request` for validation failures
- `404 Not Found` for non-existent feedback IDs
- `201 Created` for successful feedback creation

## 🚀 Production Deployment

1. **Build Production JAR**: `./gradlew bootJar`
2. **Database Setup**: Configure production MongoDB instance
3. **Environment Variables**: Set MongoDB URI and other config
4. **Remove Testcontainers**: Replace with production database configuration
5. **Health Checks**: Add Spring Boot Actuator for monitoring
6. **Logging**: Configure appropriate logging levels
7. **Security**: Add authentication/authorization if needed

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request
