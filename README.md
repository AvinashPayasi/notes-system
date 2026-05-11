# Notes System API

A backend-focused Notes Management API built using Java, Spring Boot, and PostgreSQL.

This project focuses on backend engineering concepts such as authentication, pagination, validation, layered architecture, state-based note management, and REST API design.

The project is designed as a production-style backend learning project rather than a simple CRUD application.

---

# Features

## Authentication
- User registration
- User login
- JWT-based stateless authentication
- Password hashing using BCrypt
- User-isolated note access

---

## Notes Management
- Create notes
- Fetch notes
- Update notes
- Soft delete notes
- Restore deleted notes
- Permanently delete notes

---

## Note States
Each note supports lifecycle-based state management:

- ACTIVE
- ARCHIVE
- TRASH

Supported operations:
- Archive / Unarchive notes
- Pin / Unpin notes
- Restore notes from trash

---

## Pagination & Sorting
The API supports:
- Pagination
- Sorting
- Direction-based ordering

Supported query parameters:
- `page`
- `size`
- `sort`
- `direction`
- `state`

---

## Validation & Exception Handling
- Request validation
- Global exception handling
- Consistent API response structure
- Proper HTTP status codes

---

# Tech Stack

## Backend
- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- PostgreSQL
- JWT Authentication

## Build Tool
- Maven

---

# Project Structure

```text
notes-system

├── controller
├── convertor
├── dto
├── entity
│   ├── enums
├── exception
├── NotesSystemApplication.java
├── repository
├── security
├── service
```

---

# Architecture

```text
Controller → Service → Repository → PostgreSQL
```

Authentication Flow:

```text
Client → JWT Authentication Filter → Spring Security → API
```

---

# API Endpoints

## Authentication

| Method | Endpoint | Description |
|--------|-----------|-------------|
| POST | `/api/v1/notes/register` | Register user |
| POST | `/api/v1/notes/login` | Login user |

---

## Notes

| Method | Endpoint | Description |
|--------|-----------|-------------|
| GET | `/api/v1/notes` | Fetch paginated notes with filtering and sorting |
| POST | `/api/v1/notes` | Create note |
| GET | `/api/v1/notes/{noteId}` | Fetch note by ID |
| PATCH | `/api/v1/notes/{noteId}` | Update note |
| DELETE | `/api/v1/notes/{noteId}` | Move note to trash |
| PATCH | `/api/v1/notes/{noteId}/restore` | Restore trashed note |
| DELETE | `/api/v1/notes/{noteId}/purge` | Permanently delete note |

---

## Note State Operations

| Method | Endpoint | Description |
|--------|-----------|-------------|
| PATCH | `/api/v1/notes/{noteId}/pin` | Pin note |
| PATCH | `/api/v1/notes/{noteId}/unpin` | Unpin note |
| PATCH | `/api/v1/notes/{noteId}/archive` | Archive note |
| PATCH | `/api/v1/notes/{noteId}/unarchive` | Unarchive note |

---

# Query Parameters

## Fetch Notes

```http
GET /api/v1/notes?page=1&size=10&sort=createdAt&direction=asc&state=ACTIVE
```

Supported query parameters:

| Parameter | Description | Default |
|-----------|-------------|---------|
| `page` | Page number | `1` |
| `size` | Number of notes per page | `10` |
| `sort` | Sort field (`createdAt`, `title`, `noteId`) | `createdAt` |
| `direction` | Sort direction (`asc`, `desc`) | `asc` |
| `state` | Filter notes by state (`ACTIVE`, `ARCHIVE`, `TRASH`) | `ACTIVE` |

---

## Fetch Single Note By State

```http
GET /api/v1/notes/{noteId}?state=TRASH
```

Supported states:
- `ACTIVE`
- `ARCHIVE`
- `TRASH`

---

# Setup

## 1. Clone Repository

```bash
git clone https://github.com/AvinashPayasi/notes-system.git
cd notes-system
```

---

## 2. Configure PostgreSQL

Create:

```text
notes-api/src/main/resources/application.properties
```

Example configuration:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/notes_system
spring.datasource.username=your_username
spring.datasource.password=your_password

jwt.secret=your_base64_secret
jwt.expiration=86400000
```

---

## 3. Run Application

```bash
./mvnw -pl notes-api spring-boot:run
```

Application runs at:

```text
http://localhost:8080
```

---

# Example Request

## Register User

```http
POST /api/v1/notes/register
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123"
}
```

---

## Login User

```http
POST /api/v1/notes/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123"
}
```

---

## Create Note

```http
POST /api/v1/notes
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json

{
  "title": "Spring Notes",
  "note": "Learning backend engineering"
}
```

---

# Current Focus

Current development focus includes:
- Logging
- Testing
- API refinement
- Backend architecture improvements
- Production-oriented backend practices

---

# Future Improvements

Planned backend engineering improvements:
- Rate limiting
- Refresh token support
- Audit logging
- API documentation
- Caching
- Monitoring & observability
- Database migrations
- Integration testing
- Docker support
- CI/CD pipeline

---

# Project Goals

This project is being built to explore:
- Backend engineering practices
- Authentication & authorization
- API architecture
- Stateful resource management
- Database interaction patterns
- Production-oriented backend development
- Scalable backend design concepts