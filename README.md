# Dog Catalog API

A simple REST API for managing a dog catalog.

The project was created as a personal pet project to practice Java backend development with Spring Boot, Spring Web, Spring Data JPA, Hibernate and PostgreSQL.

## Technologies

* Java 17
* Spring Boot
* Spring Web
* Spring Data JPA
* Hibernate
* PostgreSQL
* Maven
* Docker

## Features

* Get all dogs
* Get a dog by ID
* Find dogs by breed
* Add a new dog
* Update an existing dog
* Delete a dog
* Request validation
* Global exception handling

## API Endpoints

### Get all dogs

```http
GET /dogs
```

Returns all dogs.

### Get dogs by breed

```http
GET /dogs?breed=Labrador
```

Returns all dogs with the specified breed.

### Get dog by ID

```http
GET /dogs/{id}
```

Example:

```http
GET /dogs/1
```

### Add a dog

```http
POST /dogs
```

Example request body:

```json
{
  "name": "Max",
  "breed": "Labrador",
  "age": 4,
  "weight": 28.5
}
```

### Update a dog

```http
PATCH /dogs/{id}
```

Only the fields provided in the request are updated.

Example:

```json
{
  "age": 5,
  "weight": 30.0
}
```

### Delete a dog

```http
DELETE /dogs/{id}
```

Example:

```http
DELETE /dogs/1
```

## Validation

The API validates incoming data.

For creating a dog:

* `name` must not be blank
* `breed` must not be blank
* `age` must be 0 or greater
* `weight` must be greater than 0

For updating a dog:

* `age` must be 0 or greater
* `weight` must be greater than 0

Invalid requests return HTTP `400 Bad Request`.

If a dog with the requested ID does not exist, the API returns HTTP `404 Not Found`.

## Project Structure

```text
src/main/java/org/example2/dogsw
├── DogController.java
├── DogService.java
├── DogRepository.java
├── DogEntity.java
├── DogDTO.java
├── PatchingDogDTO.java
└── GlobalExceptionHandler.java
```

### Main components

**DogController**

Handles HTTP requests and maps them to service methods.

**DogService**

Contains the main application logic and converts entities to DTOs.

**DogRepository**

Uses Spring Data JPA to communicate with the database.

**DogEntity**

Represents the `Dogs` table in PostgreSQL.

**DogDTO**

Used for creating and returning dog data.

**PatchingDogDTO**

Used for partial updates with `PATCH`.

**GlobalExceptionHandler**

Handles validation errors and requests for non-existent dogs.

## Database

The project uses PostgreSQL.

PostgreSQL can be started using Docker Compose:

```bash
docker compose up -d
```

The Docker Compose configuration creates a PostgreSQL database named `postgres` with the following local development configuration:

```text
Host: localhost
Port: 5432
Database: postgres
Username: postgres
Password: root
```

The application uses Hibernate to create and update the database schema automatically.

## Running the Project

### 1. Clone the repository

```bash
git clone https://github.com/RalfsGrinbergs/dog-catalog-api-pet.git
```

### 2. Open the project

Open the project in IntelliJ IDEA or another Java IDE.

### 3. Start PostgreSQL

From the project root:

```bash
docker compose up -d
```

### 4. Run the application

Run the Spring Boot application from the IDE or using Maven:

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

The API will then be available at:

```text
http://localhost:8080
```

## Example

Request:

```http
POST /dogs
Content-Type: application/json
```

```json
{
  "name": "Buddy",
  "breed": "Golden Retriever",
  "age": 3,
  "weight": 29.5
}
```

Response:

```json
{
  "id": 1,
  "name": "Buddy",
  "breed": "Golden Retriever",
  "age": 3,
  "weight": 29.5
}
```
