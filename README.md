# Demo Todos Backend application
This is a spring boot application for backend of "Demo Todos" application. It exposes REST API to do CRUD operations on Todo items.

# Architecture
TODO

# Local testing
## Build the app
```sh
mvn clean package
```

## Start/Stop Cassandra container
Start
```sh
docker compose up -d
```

Stop
```sh
docker compose stop
```

## Run the app
```sh
java -jar target/demo-todos-0.0.1.SNAPSHOT.jar
```

