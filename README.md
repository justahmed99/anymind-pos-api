# AnyMind POS API

## Technologies
Technologies used in this project :
- Spring Boot (Kotlin)
- JPA
- gRPC
- PostgreSQL
- Gradle
- FlyWay

## How To Run This Project
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
- After the build success, you must migrate the schema by using this FlyWay command :
```
./gradlew flywayMigrate
```
- Finally, you can run the project with this command :
```
./gradlew bootRun
```

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