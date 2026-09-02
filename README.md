# Task Tracker

Task Tracker is a Spring Boot REST API for organizing tasks into task lists. It uses Spring Data JPA for persistence and PostgreSQL as the default runtime database.

## Tech stack

- Java 21
- Spring Boot 4.1.1
- Spring Web MVC
- Spring Data JPA / Hibernate
- PostgreSQL
- Maven

## Prerequisites

- JDK 21 or later
- Docker Desktop (recommended for PostgreSQL)

## Getting started

1. Start PostgreSQL:

   ```powershell
   docker compose up -d
   ```

2. Run the application:

   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

   The API starts on `http://localhost:8080`.

The default application configuration connects to PostgreSQL on `localhost:5432` using database `postgres`, username `postgres`, and the password configured in `src/main/resources/application.properties`. Update that file for a different database configuration.

## API endpoints

### Task lists

| Method | Endpoint | Description |
| --- | --- | --- |
| `GET` | `/taskLists` | List all task lists |
| `POST` | `/taskLists` | Create a task list |
| `GET` | `/taskLists/{task_list_id}` | Get a task list |
| `PUT` | `/taskLists/{task_list_id}` | Replace a task list |
| `DELETE` | `/taskLists/{task_list_id}` | Delete a task list |

### Tasks

| Method | Endpoint | Description |
| --- | --- | --- |
| `GET` | `/taskLists/{task_list_id}/tasks` | List tasks in a task list |
| `POST` | `/taskLists/{task_list_id}/tasks` | Create a task |
| `GET` | `/taskLists/{task_list_id}/tasks/{task_id}` | Get a task |
| `PUT` | `/taskLists/{task_list_id}/tasks/{task_id}` | Replace a task |
| `PATCH` | `/taskLists/{task_list_id}/tasks/{task_id}` | Partially update a task |
| `DELETE` | `/taskLists/{task_list_id}/tasks/{task_id}` | Delete a task |

Task priorities are `HIGH`, `MEDIUM`, and `LOW`. Task statuses are `OPEN` and `CLOSE`.

### Example requests

Create a task list:

```http
POST /taskLists
Content-Type: application/json

{
  "title": "Work",
  "description": "Work-related tasks"
}
```

Create a task:

```http
POST /taskLists/{task_list_id}/tasks
Content-Type: application/json

{
  "title": "Prepare report",
  "description": "Finish the monthly report",
  "dueDate": "2026-09-10T17:00:00",
  "taskStatus": "OPEN",
  "taskPriority": "HIGH"
}
```

## Project structure

```text
src/main/java/com/app/task_tracker/
|-- controllers/       REST controllers and exception handling
|-- domain/            Entities, DTOs, and enums
|-- mappers/           Entity/DTO mapping
|-- reposoteries/      Spring Data repositories
`-- services/          Application services
```

## Testing

Run the test suite with:

```powershell
.\mvnw.cmd test
```

Tests use an in-memory H2 database configured in `src/test/resources/application.properties`.
