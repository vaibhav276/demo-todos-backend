# Demo Todos Backend application
This is a spring boot application for backend of "Demo Todos" application. It exposes REST API to do CRUD operations on Todo items.

# Architecture
TODO

# Local testing
## Build the app
```sh
mvn clean package
```

## Start dependency containers
Start
```sh
# First time
docker compose -f docker-compose.yml up -d

# Start a previously stopped state
docker compose -f docker-compose.yml start -d
```

Stop
```sh
# Stopping to preserve state
docker compose -f docker-compose.yml stop

# Destroy state
docker compose -f docker-compose.yml down
```

## Run the app
```sh
java -jar target/demo-todos-0.0.1.SNAPSHOT.jar
```

