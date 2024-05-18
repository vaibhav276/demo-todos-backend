# Demo Todos Backend application
This is a spring boot application for backend of "Demo Todos" application. It exposes REST API to do CRUD operations on Todo items.

# Architecture
## Database and stores
The application uses [Apache Cassandra](https://cassandra.apache.org/) noSQL database to store Todo items with following properties:
* `user_id` (Primary key)
* `todo_id` (Sort key)
* `due_date`
* `title`
* `description`
* `done`

Its a basic schema on which an efficient CRUD API for Todos is built.

## REST APIs
REST APIs are implemented in [Java](https://www.java.com/) using [Spring Boot](https://spring.io/projects/spring-boot).

## API Documentation
API is documented using [Open API](https://www.openapis.org/what-is-openapi) specs.

# Local testing
## Build the app
```sh
mvn clean package
```

## Start dependency containers
Start
```sh
# First time
docker compose up -d

# Start a previously stopped state
docker compose start
```

Stop
```sh
# Stopping to preserve state
docker compose stop

# Destroy state
docker compose down
```

## Run the app
```sh
java -jar target/demo-todos-0.0.1.SNAPSHOT.jar
```

