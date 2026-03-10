# Secure Notes

Secure Notes is a **multi-module Java application** for managing notes using a **Spring Boot REST API** and a **command-driven CLI client**.

The project demonstrates backend architecture, API design, and building a custom client that communicates with a backend service over HTTP.

---

# Project Architecture

```
secure-notes
├── notes-api   → Spring Boot REST API
├── notes-cli   → Java command-line client
└── pom.xml     → Multi-module Maven project
```

### Request Flow

```
CLI → HTTP Client → Spring Boot API → Service → Repository → PostgreSQL
```

The CLI communicates with the backend using HTTP requests and parses JSON responses using **Jackson**.

---

# Features

## Notes Management
- Create notes
- View notes
- Delete notes (soft delete)
- Recover deleted notes
- Permanently delete notes

## Note States
- Pin / Unpin notes
- Archive / Unarchive notes
- Trash management

## Pagination
The Notes API supports **paginated responses** for efficient retrieval of notes.

## Command-Driven CLI

The CLI uses a **command-based interface** instead of menu navigation.

Example commands:

```
secure-notes-> notes
secure-notes-> note 3
secure-notes-> add
secure-notes-> delete 5
secure-notes-> pin 2
secure-notes-> archive 4
secure-notes-> trash
secure-notes-> recover 3
secure-notes-> purge 6
secure-notes-> quit
```

---

# Tech Stack

## Backend
- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL

## Client
- Java
- Java HTTP Client
- Jackson (JSON parsing)

## Build Tool
- Maven (multi-module project)

---

# Setup

## 1. Clone Repository

```bash
git clone https://github.com/AvinashPayasi/secure-notes.git
cd secure-notes
```

## 2. Configure Database

Copy the example configuration:

```bash
cp notes-api/src/main/resources/application-example.properties \
   notes-api/src/main/resources/application.properties
```

Edit `application.properties` and fill in your PostgreSQL credentials.

---

## 3. Run Backend API

```bash
./mvnw -pl notes-api spring-boot:run
```

The API will start at:

```
http://localhost:8080
```

---

## 4. Run CLI Client

```bash
./mvnw -pl notes-cli exec:java
```

You will see the CLI prompt:

```
secure-notes->
```

---

# Project Goals

This project explores:

- REST API design with Spring Boot
- Layered backend architecture
- Building custom clients for APIs
- Command-driven CLI applications
- Managing note states (pin, archive, trash)

---

# Future Improvements

- Authentication and user accounts
- End-to-end encryption for notes
- API validation and exception handling