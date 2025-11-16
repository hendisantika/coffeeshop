# Coffee Shop

A Spring Boot REST API application that manages coffee shop store locations with MongoDB backend and geospatial querying
support.

## Features

- **REST API** - Exposes store data via Spring Data REST with HATEOAS support
- **MongoDB Integration** - Uses Spring Data MongoDB for data persistence
- **Geospatial Indexing** - Supports location-based queries with 2DSphere indexing
- **Sample Data** - Auto-loads sample Starbucks store locations on startup
- **HAL Explorer** - Built-in API browser for exploring endpoints

## Tech Stack

- Java 21
- Spring Boot 3.5.7
- Spring Data MongoDB
- Spring Data REST
- Lombok
- MongoDB 7.0
- TestContainers (for testing)

## Prerequisites

- JDK 21 or higher
- Maven 3.6.3 or higher
- Docker (for running MongoDB)

## Getting Started

### 1. Start MongoDB

Using Docker:

```bash
docker run -d --name coffeeshop-mongo -p 27017:27017 mongo:7.0
```

Or use Docker Compose:

```bash
docker-compose up -d
```

### 2. Build the Application

```bash
./mvnw clean package -DskipTests
```

### 3. Run the Application

```bash
java -jar target/coffeeshop-0.0.1-SNAPSHOT.jar
```

Or using Maven:

```bash
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### Root Endpoint

```bash
curl http://localhost:8080
```

Returns:

```json
{
  "_links": {
    "stores": {
      "href": "http://localhost:8080/stores{?page,size,sort*}",
      "templated": true
    },
    "profile": {
      "href": "http://localhost:8080/profile"
    }
  }
}
```

### List All Stores

```bash
curl http://localhost:8080/stores
```

### Get Store by ID

```bash
curl http://localhost:8080/stores/{id}
```

### Search Stores by Location (Geospatial Query)

```bash
curl "http://localhost:8080/stores/search/by-location?location={longitude},{latitude}&distance={distance}"
```

### Pagination

```bash
curl "http://localhost:8080/stores?page=0&size=5&sort=name,asc"
```

## HAL Explorer

Access the HAL Explorer at:

```
http://localhost:8080/explorer
```

This provides an interactive UI to explore and test the REST API.

## Project Structure

```
src/main/java/com/hendisantika/coffeshop/
├── CoffeshopApplication.java    # Main application entry point
├── StoreInitializer.java        # Loads sample data from CSV
├── entity/
│   └── Store.java               # Store entity with Address nested class
└── repository/
    └── StoreRepository.java     # MongoDB repository interface
```

## Sample Data

The application automatically loads 10 sample Starbucks store locations on first startup from
`src/main/resources/starbucks.csv`. The stores are located in the Pacific Northwest region (Seattle, Bellevue, Redmond,
etc.).

## Configuration

Default configuration in `application.properties`:

- MongoDB connection: `localhost:27017`
- Database name: `test` (default)
- Server port: `8080`

To customize, create `application.properties` or `application.yml`:

```properties
spring.data.mongodb.uri=mongodb://localhost:27017/coffeeshop
server.port=8080
spring.thymeleaf.check-template-location=false
```

## Running Tests

Tests require Docker to be running (uses TestContainers):

```bash
./mvnw test
```

## Building for Production

```bash
./mvnw clean package -DskipTests
```

The executable JAR will be created at `target/coffeeshop-0.0.1-SNAPSHOT.jar`

## Docker Compose (Optional)

Create a `docker-compose.yml`:

```yaml
version: '3.8'
services:
  mongodb:
    image: mongo:7.0
    container_name: coffeeshop-mongo
    ports:
      - "27017:27017"
    volumes:
      - mongodb_data:/data/db

  app:
    build: .
    ports:
      - "8080:8080"
    depends_on:
      - mongodb
    environment:
      - SPRING_DATA_MONGODB_URI=mongodb://mongodb:27017/coffeeshop

volumes:
  mongodb_data:
```

## License

This project is open source and available under the MIT License.

## Author

- Hendi Santika
- Email: hendisantika@gmail.com
- Telegram: @hendisantika34
