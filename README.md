# Spring Boot Kotlin Project

A modern Spring Boot application built with Kotlin, featuring Spring Data JDBC, Flyway migrations, and comprehensive testing setup.

## Features

- **Kotlin** - Modern JVM language with concise syntax
- **Spring Boot 3.2.0** - Latest stable version with Java 17 support
- **Spring Data JDBC** - Lightweight data access layer
- **Flyway** - Database migration management
- **PostgreSQL** - Robust relational database
- **Testcontainers** - Integration testing with real database containers
- **Kluent** - Fluent assertion library for Kotlin
- **MockK** - Mocking library for Kotlin
- **Caffeine Cache** - High-performance caching
- **Spring Security** - Security framework
- **Actuator** - Production-ready monitoring and metrics

## Project Structure

```
src/
├── main/
│   ├── kotlin/com/example/demo/
│   │   ├── DemoApplication.kt          # Main application class
│   │   ├── controller/
│   │   │   └── UserController.kt       # REST API endpoints
│   │   ├── model/
│   │   │   └── User.kt                 # Data model
│   │   ├── repository/
│   │   │   └── UserRepository.kt       # Data access layer
│   │   └── service/
│   │       └── UserService.kt          # Business logic
│   └── resources/
│       ├── application.properties      # Application configuration
│       └── db/migration/               # Flyway migration scripts
│           ├── V1__Create_users_table.sql
│           └── V2__Insert_sample_data.sql
└── test/
    ├── kotlin/com/example/demo/
    │   ├── DemoApplicationTests.kt     # Application context tests
    │   ├── TestConfiguration.kt        # Test configuration
    │   ├── controller/
    │   │   └── UserControllerTest.kt   # Controller tests
    │   ├── repository/
    │   │   └── UserRepositoryTest.kt   # Repository tests
    │   └── service/
    │       └── UserServiceTest.kt      # Service tests
    └── resources/
        ├── application-test.properties # Test configuration
        └── init-test.sql              # Test database initialization
```

## Prerequisites

- Java 17 or higher
- Docker (for Testcontainers)
- PostgreSQL (for local development)

## Getting Started

### 1. Clone and Setup

```bash
git clone <repository-url>
cd fcis-spring
```

### 2. Database Setup

Create a PostgreSQL database and user:

```sql
CREATE DATABASE demo_db;
CREATE USER demo_user WITH PASSWORD 'demo_password';
GRANT ALL PRIVILEGES ON DATABASE demo_db TO demo_user;
```

### 3. Run the Application

```bash
./gradlew bootRun
```

The application will start on `http://localhost:8080`

### 4. Run Tests

```bash
./gradlew test
```

## API Endpoints

### Users

- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `GET /api/users/username/{username}` - Get user by username
- `GET /api/users/search?name={name}` - Search users by name
- `POST /api/users` - Create new user
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user

### Example API Usage

```bash
# Get all users
curl http://localhost:8080/api/users

# Create a new user
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "newuser",
    "email": "newuser@example.com",
    "firstName": "New",
    "lastName": "User"
  }'

# Search users
curl http://localhost:8080/api/users/search?name=John
```

## Monitoring

The application includes Spring Boot Actuator for monitoring:

- Health check: `http://localhost:8080/actuator/health`
- Metrics: `http://localhost:8080/actuator/metrics`
- Prometheus metrics: `http://localhost:8080/actuator/prometheus`

## Testing

The project includes comprehensive testing setup:

- **Unit Tests** - Service and controller tests with MockK
- **Integration Tests** - Repository tests with Testcontainers
- **Web Tests** - Controller tests with MockMvc
- **Kluent Assertions** - Fluent and readable test assertions

### Running Specific Tests

```bash
# Run all tests
./gradlew test

# Run specific test class
./gradlew test --tests "UserServiceTest"

# Run tests with coverage
./gradlew test jacocoTestReport
```

## Configuration

### Application Properties

Key configuration options in `application.properties`:

- Database connection settings
- Flyway migration configuration
- Logging levels
- Actuator endpoints

### Test Configuration

Test-specific settings in `application-test.properties`:

- Testcontainers database configuration
- Disabled security for easier testing
- Debug logging for tests

## Dependencies

### Core Dependencies

- `spring-boot-starter-web` - Web MVC framework
- `spring-boot-starter-data-jdbc` - JDBC data access
- `spring-boot-starter-validation` - Bean validation
- `spring-boot-starter-security` - Security framework
- `spring-boot-starter-cache` - Caching support

### Database

- `postgresql` - PostgreSQL JDBC driver
- `flyway-core` - Database migration tool

### Testing

- `spring-boot-starter-test` - Spring Boot test starter
- `testcontainers` - Integration testing
- `kluent` - Fluent assertions
- `mockk` - Kotlin mocking library

### Utilities

- `jackson-module-kotlin` - Kotlin JSON support
- `caffeine` - High-performance cache
- `commons-lang3` - Apache Commons utilities

## Development

### Code Style

The project uses Kotlin's official code style. Configure your IDE to use:

- Kotlin code style: `official`
- JVM target: `17`
- Language version: `1.9`

### Database Migrations

Add new migrations in `src/main/resources/db/migration/`:

- Follow naming convention: `V{version}__{description}.sql`
- Use incremental version numbers
- Test migrations with Testcontainers

### Adding New Features

1. Create model classes in `model/` package
2. Add repository interfaces in `repository/` package
3. Implement business logic in `service/` package
4. Create REST endpoints in `controller/` package
5. Write comprehensive tests

## Production Deployment

### Environment Variables

Set these environment variables for production:

```bash
SPRING_DATASOURCE_URL=jdbc:postgresql://your-db-host:5432/your-db
SPRING_DATASOURCE_USERNAME=your-username
SPRING_DATASOURCE_PASSWORD=your-password
```

### Docker Support

The application can be containerized:

```dockerfile
FROM openjdk:17-jdk-slim
COPY build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Ensure all tests pass
6. Submit a pull request

## License

This project is licensed under the MIT License.
