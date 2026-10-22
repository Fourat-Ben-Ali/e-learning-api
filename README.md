# E-Learning API

A multilingual B2C e-learning platform for individual teachers who want to create and manage online courses, subjects, students, and teacher-to-student communication. The current project is the first backend module and focuses on subject management.

The application uses Spring Data JPA with PostgreSQL. Data is designed to be isolated per teacher workspace. Support for schools and organizations may be added later.

## Technology stack

- Java 17
- Spring Boot 4.1.1
- Spring Web MVC
- Spring Data JPA / Hibernate
- PostgreSQL 17
- Maven Wrapper
- Springdoc OpenAPI

## Product direction

The current domain is centered around individual teachers:

```text
Teacher workspace
├── Subjects
├── Courses
├── Students
├── Enrollments
└── Messages
```

The planned implementation order is:

1. Authentication and teacher accounts
2. Teacher workspaces and secure data isolation
3. Subjects
4. Courses and lessons
5. Students and enrollments
6. Teacher-to-student communication

School and organization accounts can be introduced later without making schools the primary tenant model today.

## Requirements

- Java 17 or newer
- Docker and Docker Compose

## Getting started

### 1. Start PostgreSQL

The application configuration expects PostgreSQL on port `5433`, while the Docker Compose file maps the container port to `5432` by default. Start the database on port `5433` so it matches the application configuration:

#### Windows PowerShell

```powershell
$env:DB_PORT = "5433"
docker compose up -d
```

#### Linux/macOS

```bash
DB_PORT=5433 docker compose up -d
```

The default database configuration is:

| Setting | Value |
| --- | --- |
| Database | `elearning-db` |
| Username | `postgres` |
| Password | `postgres` |
| Host port | `5433` |

### 2. Run the application

#### Windows

```powershell
.\mvnw.cmd spring-boot:run
```

#### Linux/macOS

```bash
./mvnw spring-boot:run
```

The API starts at `http://localhost:8080`.

To stop PostgreSQL:

```bash
docker compose down
```

## API

The current subject endpoints require the `X-Tenant-ID` header. This is a temporary development mechanism. In the planned authenticated architecture, the current teacher workspace will be resolved from the authenticated user instead of trusting a tenant ID supplied by the client.

| Method | Endpoint | Description |
| --- | --- | --- |
| `POST` | `/api/v1/subjects` | Create a subject |
| `GET` | `/api/v1/subjects` | List subjects for the current tenant |
| `GET` | `/api/v1/subjects/{subjectId}` | Get a subject |
| `PUT` | `/api/v1/subjects/{subjectId}` | Update a subject |
| `DELETE` | `/api/v1/subjects/{subjectId}` | Delete a subject |

### Create a subject

```bash
curl -X POST http://localhost:8080/api/v1/subjects \
  -H "Content-Type: application/json" \
  -H "X-Tenant-ID: tenant-a" \
  -d '{
    "name": "Introduction to Java",
    "description": "Java fundamentals for beginners",
    "imageUrl": "https://example.com/java.png"
  }'
```

### List subjects

```bash
curl http://localhost:8080/api/v1/subjects \
  -H "X-Tenant-ID: tenant-a"
```

## API documentation

When the application is running, OpenAPI documentation is available at:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Build and test

Run the test suite with the Maven Wrapper:

```bash
./mvnw test
```

On Windows:

```powershell
.\mvnw.cmd test
```

Build the application:

```bash
./mvnw clean package
```

## Project structure

```text
src/
├── main/
│   ├── java/org/elearning/
│   │   ├── config/          # Tenant context and Hibernate filtering
│   │   ├── controllers/     # REST controllers
│   │   ├── entities/        # JPA entities
│   │   ├── mappers/         # Entity/DTO mapping
│   │   ├── repositories/    # Spring Data repositories
│   │   ├── requests/        # Request DTOs
│   │   └── services/        # Business logic
│   └── resources/
│       └── application.properties
└── test/
    └── java/                # Application tests
```

## Configuration

Application settings are defined in `src/main/resources/application.properties`. For local or production use, provide database credentials through a secure configuration mechanism rather than committing secrets to the repository.

## Localization

The platform is intended to support multiple languages.

- Use Spring `MessageSource` files such as `messages.properties`, `messages_fr.properties`, and `messages_ar.properties` for system messages, validation messages, and errors.
- Use a translation table for teacher-created content such as subjects, courses, and lessons.
- Store language codes using values such as `en`, `fr`, and `ar`.
- Resolve the requested language from the `Accept-Language` header.
- Use a fallback order of requested language, teacher/workspace default language, then English.

For multilingual database content, prefer related translation records over columns such as `nameEn`, `nameFr`, and `nameAr`. A typical subject structure will be:

```text
Subject
└── translations
    ├── languageCode: en
    ├── name
    └── description
```

Arabic and other right-to-left languages will also require corresponding frontend support.
