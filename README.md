# Social Club Management System

A Spring Boot-based REST API for managing a social club's members, finances, and charitable activities.

## Technologies

- **Java 26** – Core language
- **Spring Boot 4.1.0** – Application framework
- **Spring Data JPA** – ORM with Hibernate
- **PostgreSQL** – Production-grade relational database
- **Lombok** – Boilerplate reduction
- **MapStruct** – DTO to Entity mapping
- **Docker** – PostgreSQL container for development
- **Maven** – Build tool

## Getting Started

### Prerequisites
- JDK 26 or later
- Docker and Docker Compose (for PostgreSQL)
- Maven (or use the included `mvnw` wrapper)

### 1. Clone the repository
```
git clone git@github.com:ulises-mandujano/Social_club_management.git
cd Social_club_management
```

### 2. Start PostgreSQL with Docker Compose
```
docker-compose up -d
```
This will start a PostgreSQL container on port 5432 with the credentials defined in your .env file (or the defaults).

### 3. Configure environment variables (optional)
Create a `.env` file in the project root with:
```
POSTGRES_USER=myuser
POSTGRES_PASSWORD=mypassword
POSTGRES_DB=mydb
```
Or rely on the defaults ```application.yaml``` (```myuser```/```mypassword```).

### 4. Run the application
```
./mvnw spring-boot:run
```

### 5. Access the application
- __API Base URL__: ```http://localhost:8080```
- __Swagger UI__: ```http://localhost:8080/swagger-ui.html```

## API Documentation
All endpoints are documented via __Swagger__/__OpenAPI__. After sharing the application, visit the Swagger UI link above
to explore and test the APIs.

### Main Endpoints
| Method     | Endpoint                    | Description           |
|------------|-----------------------------|-----------------------|
| ```POST``` | ```/api/members/register``` | Register a new member |
| ```GET```  | ```/api/members```          | Retrieve all members  |

_(Additional endpoints will be added as the project evolves.)_

## Testing
Run the unit and integration tests with:
```
./mvnw test
```

## Contributing
Contributions are welcome! Please follow the standard GitHub flow;
1. Fork the repository.
2. Create the feature branch (```git checkout -b feature/amazing-feature```).
3. Commit changes.
4. Push to the branch (```git push origin feature/amazing-feature```).
5. Open a Pull Request.

## License

This project is licensed under the Apache License, Version 2.0 - see the [LICENSE](LICENSE) file for details.
Copyright [2026] [Ulises Mandujano]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.