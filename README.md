# User Service API


Spring Boot application for user registration with Basic Authentication and
role-based access control

## Java Version 17
## Spring Boot Version 3.2.6
## Task completed
- User registration API (stores IP & country)
- Basic authentication (BCrypt password encoding)
- Role-based access (Admin & User)
- JUnit tests for service layer
- Swagger-enabled
- Dockerized application setup(Not completed due to system incompetancy)

## Endpoints

1. "/api/v1/users/register"     | Register a new user         | Public  |
2. "/api/v1/users/validate"     | Validate user credentials   | Public  |
3. "/api/v1/users/all"         | Get all registered users    | Admin   |
4. "/api/v1/users/delete"      | Delete a user by email      | Admin   |

## Preloaded User Credentials

- Admin Role
  username : admin@gmail.com
  password : admin123
-  User Role
   username : user@gmail.com
   password : user123
-
## Server Port : 8080

## swagger version : 2.6.0
## Swagger URL
http://localhost:8080/swagger-ui.html

## Clone the repository using https://github.com/Srijan02112000/UserServiceDemo.git
## Running the Application
 - mvn clean install
 - mvn spring-boot:run







