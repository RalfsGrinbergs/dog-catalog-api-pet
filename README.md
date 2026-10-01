# Dog Catalog API

A REST API for managing dogs and their owners. This personal project was created to practice Java backend development with Spring Boot, Spring Data JPA, Hibernate, and PostgreSQL.

## Technologies

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- Docker
- JUnit
- Mockito

## Features

- Get all dogs
- Get a dog by ID
- Find dogs by breed
- Add a dog and assign it to an owner
- Limit each owner to a maximum of five dogs
- Partially update a dog with `PATCH`
- Delete a dog
- Create and delete owners
- Get all owners or an owner by ID
- Get all dogs belonging to an owner
- Get an owner’s dog count
- Request validation
- Global exception handling
- Unit tests for service logic

## Data model

An owner can have zero or more dogs. Each dog must belong to an existing owner, and an owner can have no more than five dogs.

The relationship is stored through a foreign key in the `Dogs` table. The owner’s `dogsCount` is calculated from the dogs in the database; it is not stored as a separate database column.

Deleting an owner also deletes that owner’s dogs.

## API Endpoints

### Dogs

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/dogs` | Get all dogs |
| `GET` | `/dogs?breed=Labrador` | Find dogs by breed |
| `GET` | `/dogs/{id}` | Get a dog by ID |
| `POST` | `/dogs` | Add a dog and assign it to an owner |
| `PATCH` | `/dogs/{id}` | Partially update a dog |
| `DELETE` | `/dogs/{id}` | Delete a dog |

### Owners

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/owners` | Get all owners |
| `GET` | `/owners/{id}` | Get an owner, including their dog count |
| `GET` | `/owners/{id}/dogs` | Get all dogs belonging to an owner |
| `POST` | `/owners` | Create an owner |
| `DELETE` | `/owners/{id}` | Delete an owner and their dogs |

## Example requests

### Add an owner

```http
POST /owners
Content-Type: application/json
```

```json
{
  "name": "Alex Morgan"
}
```

The response includes the generated ID and the current dog count:

```json
{
  "id": 1,
  "name": "Alex Morgan",
  "dogsCount": 0
}
```

### Add a dog

A dog must be assigned to an existing owner. Use the owner ID returned by `POST /owners`.

```http
POST /dogs
Content-Type: application/json
```

```json
{
  "name": "Max",
  "breed": "Labrador",
  "age": 4,
  "weight": 28.5,
  "ownerId": 1
}
```

Example response:

```json
{
  "id": 1,
  "name": "Max",
  "breed": "Labrador",
  "age": 4,
  "weight": 28.5,
  "ownerId": 1
}
```

If the owner already has five dogs, adding another dog returns HTTP `409 Conflict`.

### Get an owner and their dog count

```http
GET /owners/1
```

```json
{
  "id": 1,
  "name": "Alex Morgan",
  "dogsCount": 1
}
```

### Get an owner’s dogs

```http
GET /owners/1/dogs
```

### Update a dog

Only the fields provided in the request are updated.

```http
PATCH /dogs/1
Content-Type: application/json
```

```json
{
  "age": 5,
  "weight": 30.0
}
```

### Delete a dog

```http
DELETE /dogs/1
```

### Delete an owner

```http
DELETE /owners/1
```

A successful owner deletion returns `204 No Content`. The owner’s dogs are deleted with the owner.

## Validation and errors

When creating a dog:

- `name` and `breed` must not be blank.
- `age` must be zero or greater.
- `weight` must be greater than zero.
- `ownerId` is required and must refer to an existing owner.
- The owner must have fewer than five dogs.

When patching a dog:

- `age`, if provided, must be zero or greater.
- `weight`, if provided, must be greater than zero.

Invalid data returns HTTP `400 Bad Request`. If a requested dog or owner does not exist, the API returns HTTP `404 Not Found`. Trying to add a sixth dog to an owner returns HTTP `409 Conflict`.

## Tests

The project includes service-level unit tests using JUnit and Mockito. They cover dog creation when the owner has reached the five-dog limit, successful dog creation below the limit, and finding an owner by ID.

Run the tests with Maven:

```bash
mvn test
```

On Windows, if the Maven wrapper is present:

```bash
mvnw.cmd test
```

## Project structure

```text
src/main/java/dogs_catalog
├── controller
│   ├── DogController.java
│   └── OwnerController.java
├── dto
│   ├── DogDTO.java
│   ├── OwnerDTO.java
│   └── PatchingDogDTO.java
├── entity
│   ├── DogEntity.java
│   └── OwnerEntity.java
├── exception
│   └── OwnerDogLimitExceededException.java
├── repository
│   ├── DogRepository.java
│   └── OwnerRepository.java
├── service
│   ├── DogService.java
│   └── OwnerService.java
├── DogswApplication.java
└── GlobalExceptionHandler.java

src/test/java/dogs_catalog/service
├── DogServiceTest.java
└── OwnerServiceTest.java
```

### Main components

- **Controllers** handle HTTP requests and responses.
- **Services** contain application logic and map entities to DTOs.
- **Repositories** use Spring Data JPA to access the database.
- **Entities** represent the `Dogs` and `Owners` tables.
- **DTOs** define the data accepted and returned by the API.
- **GlobalExceptionHandler** handles validation errors, missing resources, and the owner dog limit.

## Database

The project uses PostgreSQL. Start the database with Docker Compose:

```bash
docker compose up -d
```

The default local development configuration is:

```text
Host: localhost
Port: 5432
Database: postgres
Username: postgres
Password: root
```

Hibernate creates and updates the database schema automatically.

## Running the project

1. Clone the repository:

   ```bash
   git clone https://github.com/RalfsGrinbergs/dog-catalog-api-pet.git
   ```

2. Open the project in IntelliJ IDEA or another Java IDE.

3. Start PostgreSQL from the project root:

   ```bash
   docker compose up -d
   ```

4. Run the application from the IDE, or use Maven:

   ```bash
   mvn spring-boot:run
   ```

   On Windows, if the Maven wrapper is present:

   ```bash
   mvnw.cmd spring-boot:run
   ```

The API is available at:

```text
http://localhost:8080
```
