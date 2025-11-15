# Vendor Onboarding API

A production-ready Spring Boot 3.x REST API for managing vendor onboarding with Microsoft SQL Server integration.

## 📋 Project Overview

This project is a comprehensive Spring Boot application that demonstrates enterprise-level development patterns including:

- **Framework**: Spring Boot 3.3.5 with Java 17
- **Database**: Microsoft SQL Server with Spring Data JPA
- **Architecture**: Layered architecture (Controller → Service → Repository → Entity)
- **Validation**: Bean Validation with custom error handling
- **Logging**: SLF4J with Logback configuration
- **Testing**: JUnit 5 with H2 in-memory database
- **Monitoring**: Spring Boot Actuator endpoints

## 🚀 Features

- ✅ User CRUD operations with validation
- ✅ Global exception handling with detailed error responses
- ✅ SQL Server integration with connection pooling
- ✅ Comprehensive logging with file rotation
- ✅ Email uniqueness validation
- ✅ RESTful API design with proper HTTP status codes
- ✅ Transaction management
- ✅ Production-ready configuration

## 🏗️ Project Structure

```
src/
├── main/
│   ├── java/com/example/springmssqlapi/
│   │   ├── VendorOnboardingApplication.java    # Main application class
│   │   ├── controller/
│   │   │   └── UserController.java             # REST endpoints
│   │   ├── service/
│   │   │   └── UserService.java                # Business logic
│   │   ├── repository/
│   │   │   └── UserRepository.java             # Data access layer
│   │   ├── entity/
│   │   │   └── User.java                       # JPA entity
│   │   └── exception/
│   │       ├── GlobalExceptionHandler.java     # Global error handling
│   │       ├── ResourceNotFoundException.java  # Custom exceptions
│   │       ├── BadRequestException.java
│   │       └── ErrorResponse.java              # Error response model
│   └── resources/
│       ├── application.properties              # Main configuration
│       └── logback-spring.xml                 # Logging configuration
└── test/
    ├── java/com/example/springmssqlapi/
    │   └── VendorOnboardingApplicationTests.java
    └── resources/
        └── application-test.properties         # Test configuration
```

## 🛠️ Technology Stack

| Component | Technology |
|-----------|------------|
| **Framework** | Spring Boot 3.3.5 |
| **Language** | Java 17 |
| **Build Tool** | Maven |
| **Database** | Microsoft SQL Server |
| **ORM** | Spring Data JPA / Hibernate |
| **Validation** | Bean Validation (Hibernate Validator) |
| **Logging** | SLF4J + Logback |
| **Testing** | JUnit 5 + H2 Database |
| **Monitoring** | Spring Boot Actuator |
| **Code Generation** | Lombok |

## 📊 Database Configuration

The application is configured to connect to:

- **Host**: `evokehackathonsqlserver.database.windows.net`
- **Database**: `PromptSmithsDB`
- **Username**: `PromptSmithsSQLUser`
- **Context Path**: `/vendoronboarding`

## 🔗 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/vendoronboarding/users` | Create a new user |
| `GET` | `/vendoronboarding/users` | Get all users |
| `GET` | `/vendoronboarding/users/{id}` | Get user by ID |
| `GET` | `/vendoronboarding/users/email/{email}` | Get user by email |
| `PUT` | `/vendoronboarding/users/{id}` | Update user |
| `DELETE` | `/vendoronboarding/users/{id}` | Delete user |

### Actuator Endpoints

| Endpoint | Description |
|----------|-------------|
| `/vendoronboarding/actuator/health` | Application health status |
| `/vendoronboarding/actuator/info` | Application information |
| `/vendoronboarding/actuator/metrics` | Application metrics |

## 📝 User Entity

```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@example.com",
  "emailVerified": false
}
```

### Validation Rules

- **name**: Required, not blank
- **email**: Required, valid email format, unique
- **emailVerified**: Boolean, defaults to false

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- Access to Microsoft SQL Server instance

### Running the Application

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd vendor-onboarding
   ```

2. **Configure database connection**
   Update `src/main/resources/application.properties` if needed

3. **Build the application**
   ```bash
   mvnw clean install
   ```

4. **Run the application**
   ```bash
   mvnw spring-boot:run
   ```

5. **Access the application**
   - API Base URL: `http://localhost:8080/vendoronboarding`
   - Health Check: `http://localhost:8080/vendoronboarding/actuator/health`

### Testing

Run tests with:
```bash
mvnw test
```

Tests use H2 in-memory database for isolation.

## 📄 API Usage Examples

### Create User
```bash
curl -X POST http://localhost:8080/vendoronboarding/users \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","email":"john@example.com"}'
```

### Get All Users
```bash
curl -X GET http://localhost:8080/vendoronboarding/users
```

### Get User by ID
```bash
curl -X GET http://localhost:8080/vendoronboarding/users/1
```

## 🔧 Configuration

### Database Configuration
Located in `application.properties`:
- Connection pooling with HikariCP
- SQL logging enabled for development
- Automatic schema updates

### Logging Configuration
Located in `logback-spring.xml`:
- Console and file logging
- Log rotation (10MB files, 30 days retention)
- Separate error log file
- Async logging for performance

## 📈 Monitoring and Observability

- **Health Checks**: Spring Boot Actuator health endpoint
- **Metrics**: Application metrics via Actuator
- **Logging**: Structured logging with correlation IDs
- **Error Tracking**: Comprehensive exception handling

## 🔒 Error Handling

The application provides consistent error responses:

```json
{
  "timestamp": "2024-11-15T10:30:00.000",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/vendoronboarding/users",
  "details": ["email: Email should be valid"]
}
```

## 🚀 Production Considerations

- ✅ Connection pooling configured
- ✅ Transaction management
- ✅ Comprehensive logging
- ✅ Error handling and validation
- ✅ Health checks and monitoring
- ✅ Security headers (can be enhanced with Spring Security)
- ✅ Environment-specific configurations

## 📚 Dependencies

Key dependencies include:
- `spring-boot-starter-web`: Web framework
- `spring-boot-starter-data-jpa`: Data persistence
- `spring-boot-starter-validation`: Bean validation
- `spring-boot-starter-actuator`: Monitoring
- `mssql-jdbc`: SQL Server driver
- `lombok`: Code generation
- `h2`: In-memory database for testing

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📄 License

This project is licensed under the MIT License.

---

**Built with ❤️ using Spring Boot 3.x and Java 17**

