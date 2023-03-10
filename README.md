# AnyMind POS API

## Technologies
Technologies used in this project :
- Spring Boot (Kotlin)
- JPA
- gRPC
- PostgreSQL
- Gradle
- FlyWay
- Docker (Optional)

## How To Run This Project

### (OPTION 1) Run With booRun Command
To run this project, you must follow these steps :
- Set `application.yml` database setup according to your local setup. change `pg_host`, `pg_port`, `db_name`, `db_user`, `db_password` according to your local PostgreSQL setup
```yaml
spring:
  datasource:
    url: jdbc:postgresql://[pg_host]:[pg_port]/[db_name]
    username: [db_user]
    password: [db_password]
    driver-class-name: org.postgresql.Driver
```

- build your project with this command :
```
./gradlew build
```
- Set the FlyWay setup in `build.gradle.kts` following the setup for `application.yml` above.
```
flyway {
    url = "jdbc:postgresql://[your_host]:[pg_port]/[db_name]"
    user = "[db_user]"
    password = "[db_password]"
    locations = arrayOf("filesystem:src/main/resources/db/migration")
}
```
- After the build success, you must migrate the schema by using this FlyWay command (It's **OPTIONAL**, as we already use `jpa.hibernate.ddl-auto=update`) :
```
./gradlew flywayMigrate
```
- Finally, you can run the project with this command :
```
./gradlew bootRun
```
- Now you can access the gRPC endpoints with gRPC client apps (such as gprcrul or Postman).

### (OPTION 2) Run with Docker Container
You must follow several steps to run this project inside docker container :
- You can change the setup inside `docker-compose.yml` file. 
```yaml
version: '3'
services:
  pg:
    image: postgres
    container_name: anymind-postgres
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: 12345678
      POSTGRES_DB: pos_app
    ports:
      - "2345:5432"
    networks:
      - anymind-net
  app:
    build: .
    ports:
      - "9090:9090"
      - "8080:8080"
    container_name: anymind-pos-api
    depends_on:
      - pg

    networks:
      - anymind-net

networks:
  anymind-net:
```
- Here is the Dockerfile.
```dockerfile
FROM openjdk:17-oracle
WORKDIR /app
COPY . /app
EXPOSE 9090 8080
ENTRYPOINT ["java", "-jar", "build/libs/pos-0.0.1-SNAPSHOT.jar"]
```
- Don't forget to update your `application.yml` file in **datasource** part following the setup of `pg` in `docker-compose.yml` file.
  - **Important Note** : You must assign the internal port of the PostgreSQL, not the external port (in this example the port `5432`, **not** port `2345`).
```yaml
spring:
  datasource:
    url: jdbc:postgresql://[pg_host]:[pg_internal_port]/[db_name]
    username: [db_user]
    password: [db_password]
    driver-class-name: org.postgresql.Driver
```
- Build the project by using this command :
```
./gradlew build -x test
```
- Create the container by using this command :
```
docker-compose up -d
```
- Check whether or not the container is running with `docker ps` command. If you get output just like below, your containers already run properly.
```
docker ps
CONTAINER ID   IMAGE                 COMMAND                  CREATED          STATUS          PORTS                                            NAMES
66f2626a8e00   anymind-pos-api-app   "java -jar build/lib…"   15 minutes ago   Up 15 minutes   0.0.0.0:8080->8080/tcp, 0.0.0.0:9090->9090/tcp   anymind-pos-api
192b96099e09   postgres              "docker-entrypoint.s…"   15 minutes ago   Up 15 minutes   0.0.0.0:2345->5432/tcp                           anymind-postgres
```
- Now you can access the gRPC endpoints with gRPC client apps (such as gprcrul or Postman).

## gRPC Endpoint

There are 2 endpoints in this project.

### calculateFinalPayment
Message :
```json
{
    "datetime": "2022-09-04T00:00:00Z",
    "paymentMethod": "VISA",
    "price": "1000.00",
    "priceModifier": 0.95
}
```
Response :
```json
{
    "finalPrice": "950.0",
    "points": 30
}
```

### listOfSalesPerHour
Message :
```json
{
    "startDateTime" : "2022-09-01T01:00:00Z",
    "endDateTime" : "2022-09-04T23:59:59Z"
}
```

Response :
```json
{
    "sales": [
        {
            "datetime": "2022-09-04T00:00Z",
            "sales": "2850.0",
            "points": 90
        },
        {
            "datetime": "2022-09-01T01:00Z",
            "sales": "8888.0",
            "points": 176
        },
        {
            "datetime": "2022-09-03T05:00Z",
            "sales": "8888.0",
            "points": 176
        }
    ]
}
```