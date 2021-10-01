# appsmart-test
Test task for appsmart

----------------------------------------------------------------------------------

### **Dependencies:**

Used libraries/apps:
- Spring boot 2.5.6-SNAPSHOT (data-jpa, data-rest, security, validation, web)
- Java 11
- Gradle 7.2
- Swagger 3 (OpenAPI 3.0.1)
- Lombok
- Jwt
- PostgreSQL 13
- Springdoc OpenAPI UI 1.5.2
- Mockito core
- Mapstruct 1.4.2
- Docker

----------------------------------------------------------------------------------

### **Build:**
Project contains docker-compose.yml, Dockerfile and initdb.sql scripts.

 - Build app with: gradle build -x test

 - Build docker image with: docker-compose up --build

Compose script creates container with two services and runs sql script with DB schema.

You can also remove app service from compose script and change port on db service.

Swagger UI http://localhost:7000/swagger-ui

Swagger json http://localhost:7000/api-docs

----------------------------------------------------------------------------------

### **Implemented:**

Database schema, customers and products relationship is OneToMany.

![](https://i.imgur.com/toecLUd.png)

List of implemented services, with basic documentation via OpenAPI.

![](https://i.imgur.com/MXlFqJm.png)

Added basic registration and authorization (login returns bearer token), users can register with any of two roles USER or ADMIN.
USER role only allowed to call GET and POST services, while ADMIN allowed to call any method.
