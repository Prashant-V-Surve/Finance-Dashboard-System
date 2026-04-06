# Finance Dashboard System

Project Overview 

This project is a custom role based finance data processing and access control backend built using spring boot as a backend server-side developement software.

---

## Tech Stack

- Programming Language        :          Java 17
- Application Framework       : Spring Boot 3
- Security Layer              : Spring Security + JWT
- Data Access Layer           : Spring Data JPA + Hibernate
- DataBase                    : MySQL
- Utilities & Code Generation : Lombok, MapStruct
- Object Mapping              : MapStruct (Entity to DTO mapping)

---

I have built Finance Dashboard System which is a structured backend system with REST API's, JWT based authentication, role based access control , relational database persistence, and dashboard level analytics.

---

## Project Structure

```
src/main/java/com/zorvyn/finance_dashboard_system/
│
├── controller/
│   ├── AuthorizationController.java      → Login endpoint
│   ├── UserController.java               → User CRUD
│   ├── FinancialRecordController.java    → Record CRUD + filtering
│   ├── DashboardController.java          → Dashboard summary
│   ├── InsightController.java            → Insights endpoint
│   └── AdminController.java              → Admin only actions
│
├── service/
│   ├── AuthService.java                  → Login + token generation
│   ├── UserService.java                  → User business logic
│   ├── FinancialRecordService.java       → Record business logic
│   └── DashboardService.java             → Dashboard aggregation logic
│
├── repository/
│   ├── UserRepository.java               → User DB queries
│   └── FinancialRecordRepository.java    → Record DB queries + custom JPQL
│
├── entity/
│   ├── User.java                         → User table
│   └── FinancialRecord.java              → Financial records table
│
├── dto/
│   ├── request/
│   │   ├── LoginRequest.java
│   │   ├── UserRequest.java
│   │   └── RecordRequest.java
│   └── response/
│       ├── LoginResponse.java
│       ├── UserResponse.java
│       ├── RecordResponse.java
│       └── DashboardResponse.java
│
├── mapper/
│   ├── UserMapper.java                   → MapStruct: User entity ↔ DTO
│   └── FinancialRecordMapper.java        → MapStruct: Record entity ↔ DTO
│
├── security/
│   ├── JwtService.java                   → Generate + validate JWT tokens
│   ├── JwtAuthrizationFilter.java        → Intercepts every request, extracts token
│   └── SecurityConfigurations.java       → Route-level access rules
│
├── exception/
│   ├── ResourceNotFoundException.java    → Custom 404 exception
│   └── GlobalExceptionHandler.java       → Centralized error responses
│
└── enums/
    ├── RoleType.java                     → ADMIN, ANALYST, CLIENT
    └── RecordType.java                   → INCOME, EXPENSE

```
---

## Setup Procedure

You can run this project in two ways

---

### Option 1 : Run the project with Maven

### 1. Clone the Repository
```
git clone <https://github.com/Prashant-V-Surve/Finance-Dashboard-System>

```

### 2. Create the database 
`
Create a MySQL Database named : finance_dashboard
`
### 3. Configure application.properties
open `src/main/resources/application.properties` and write :
```
spring.datasource.url=jdbc:mysql://localhost:3306/finance_dashboard
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

jwt.secret=mySecretKeyIsMyHardWork123456789
```
### 4. Open the IDE and Run the Project

```
mvn spring-boot:run
```
API will run at : 'http://localhost:8080

---

### Option 2 — Run with JAR (Quick Start)

###1. Make sure MySQL is running and database is created:
   finance_dashboard

###2. Build the JAR:
   mvn clean package -DskipTests

###3. Run the JAR:
   java -jar target/finance-dashboard-system-0.0.1-SNAPSHOT.jar

API starts at: http://localhost:8080

---

### Roles and Permission

ADMIN :  Full access and has all the permissions — Manage users(CRUD), create/Read/update/delete records,             view dashboard.

ANALYST : Has Access limited to Read records, filter records, view dashboard, view insights.

CLIENT : Has extremely limited access can View dashboard summary only.

Role-based access is checked two times while the request is generated:

- **Route level** - At `SecurityConfiguration.java' first where it is checked that which roles have the                          permission to access which URL path which is a request.
- **Method level** - By using annotation `@PreAuthorize` on each individual method in the controller which                        have their specific functions. Some roles should have permission to access them and                         some should not ,based on their type.

---

## Authentificatation Procedure

Step 1 : Registering a user via `Post /users`
Step 2 : Login via `POST /auth/login` after which you will login and receive a JWT token.
Step 3 : Copy the JWT token from the Response you received.
Step $ : Add it to every protected request as a header.

```
Authorization: Bearer <recieved_token>
```
For this project i have set the token expiration time which is **1 hour**.After 1 hour you will have to generate new token by following the same procedure explained above.

The JWT token we received contains:
- User ID (as subject)
- Email ID
- RoleType

For Filtering the token class `JwtAuthrizationFilter` is made which intercepts every request by the customer and extracts the above details from token and then further sets authentication in Spring Security's context so `@PreAuthorize` can enforce checking for the roles.

---

## API Endpoints

### Auth (Public)

| Method | Endpoint       | Description                  |
|--------|----------------|------------------------------|
| POST   | /auth/login    | Login and receive JWT token  |

**Request body:**
```json
{
  "email": "prashant@gmail.com",
  "password": "prashant123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

### Users

| Method | Endpoint             | Access | Description        |
|--------|----------------------|--------|--------------------|
| POST   | /users               | Public | Register new user  |
| GET    | /users               | ADMIN  | Get all users      |
| GET    | /users/id/{userId}   | ADMIN  | Get user by ID     |
| PUT    | /users/id/{userId}   | ADMIN  | Update user        |
| DELETE | /users/id/{userId}   | ADMIN  | Delete user        |

**Request body (POST and PUT):**
```json
{
  "name": "Prashant Surve",
  "email": "prashant@gmail.com",
  "password": "prashant123",
  "roleType": "ADMIN"
}
```

`roleType` accepted values: `ADMIN`, `ANALYST`, `CLIENT`

---

### Financial Records

| Method | Endpoint                              | Access         | Description                  |
|--------|---------------------------------------|----------------|------------------------------|
| POST   | /records                              | ADMIN          | Create a new record          |
| GET    | /records/id/{id}                      | ADMIN, ANALYST | Get all records for a user   |
| GET    | /records/id/{id}/category/{category}  | ADMIN, ANALYST | Filter records by category   |
| GET    | /records/id/{id}/type/{type}          | ADMIN, ANALYST | Filter records by type       |
| PUT    | /records/id/{id}                      | ADMIN          | Update a record              |
| DELETE | /records/id/{id}                      | ADMIN          | Delete a record              |

**Request body (POST and PUT):**
```json
{
  "amount": 5000.00,
  "description": "Monthly salary",
  "recordType": "INCOME",
  "category": "Salary",
  "date": "2026-04-01T09:00:00"
}
```

`recordType` accepted values: `INCOME`, `EXPENSE`

**Filter by type example:**
```
GET /records/id/1/type/INCOME
GET /records/id/1/type/EXPENSE
```

**Filter by category example:**
```
GET /records/id/1/category/Salary
```

Note: For `POST /records`, the logged-in admin's user ID is automatically extracted from the JWT token via `HttpServletRequest.getAttribute("userId")` — no need to pass it in the request body.Which makes it more easy for as the user will not have to remember which record he has to enter.

---

### Dashboard

| Method | Endpoint             | Access                       | Description               |
|--------|----------------------|------------------------------|---------------------------|
| GET    | /dashboard/id/{id}   | ADMIN, ANALYST, CLIENT       | Get full dashboard summary|

**Response includes:**
```json
{
  "totalIncome": 15000.00,
  "totalExpense": 8000.00,
  "netBalance": 7000.00,
  "categoryTotals": {
    "Salary": 15000.00,
    "Food": 3000.00,
    "Rent": 5000.00
  },
  "recentActivity": [...],
  "incomeTrends": {
    "1": 5000.00,
    "2": 5000.00,
    "3": 5000.00
  },
  "expenseTrends": {
    "1": 2000.00,
    "2": 3000.00,
    "3": 3000.00
  }
}
```

`incomeTrends` and `expenseTrends` keys are month numbers (1 = January, 2 = February, etc.)

---

### Insights

| Method | Endpoint   | Access          | Description  |
|--------|------------|-----------------|--------------|
| GET    | /insights  | ADMIN, ANALYST  | Get insights |

### Admin

| Method | Endpoint          | Access | Description     |
|--------|-------------------|--------|-----------------|
| POST   | /admin/create     | ADMIN  | Admin create    |
| DELETE | /admin/delete/{id}| ADMIN  | Admin delete    |

---

## Error Responses

All errors are handled globally by `GlobalExceptionHandler` and return a consistent format:

**404 - Resource Not Found:**
```json
{
  "timeStamp": "2026-04-06T10:00:00",
  "message": "User with id 5 not found!",
  "status": 404
}
```

**500 - Internal Server Error:**
```json
{
  "timeStamp": "2026-04-06T10:00:00",
  "message": "Something went wrong",
  "status": 500
}
```

**403 - Forbidden:**
Returned automatically by Spring Security when a role tries to access an endpoint they are not permitted to use.

---

## Assumptions Made

1. **User registration is public** — `POST /users` has no requirement for authentication which allow easy initial setup and testing for the ADMIN . In a Real life production system this would be restricted to ADMIN only.

2. **Passwords are stored as plain text** — Password comparison is done by inbuilt `.equals()` method in this version. I am fully aware that the production standard is BCrypt hashing using `passwordEncoder.matches()`. This was deliberately kept simple with the intention to focus on the core requirements — architecture, role-based access control, and dashboard analytics logic.

3. **Financial record dates are client-provided** — The date field in `RecordRequest` is sent by the client, not auto-generated by the server. This allows backdated entries which is a realistic requirement for finance systems where users may record past transactions.

4. **Dashboard takes user ID as a path variable** — `GET /dashboard/id/{id}` accepts the user ID in the URL. In production, this ID would be extracted directly from the JWT token so users can only ever access their own dashboard data, not other users'.

5. **The `active` field on User supports deactivation** — When a user is created, `active` is set to `true`. This field is designed to support soft delete or account suspension logic without removing the user from the database.

6. **Single role per user** — Each user has exactly one role. Multi-role assignment was not implemented as the assignment did not require it and keeping it simple makes the access control logic clearer.

---

## Tradeoffs Considered

- **MapStruct over manual mapping** — MapStruct generates mapping code at compile time. It is faster at runtime and eliminates repetitive boilerplate. The tradeoff is a slightly higher project setup overhead and compile-time dependency.

- **JWT over session-based auth** — JWT is stateless so the server does not need to store session data, making it easy to scale. The tradeoff is that tokens cannot be invalidated before expiry without extra infrastructure like a token blacklist.

- **`double` for financial amounts** — Used `double` for simplicity. The production-correct type is `BigDecimal` to avoid floating point precision errors on financial calculations such as `99.99 + 0.01` producing `99.99999999998`.

- **JPQL custom queries for dashboard** — Used `@Query` with JPQL in `FinancialRecordRepository` for category totals and monthly trends instead of pulling all data into Java and computing it there. This shifts the mathematical work for aggregating to the database which is more efficient.

- **Two-layer access control** — Route-level rules in `SecurityConfigurations` act as a first gate. Method-level `@PreAuthorize` acts as a second, more precise gate. This makes the security logic easy to read and reason about at both levels.

- **Using Postman for Testing and monitoring API's** - Using Postman for CRUD operations
testing which excels at manual testing, scripting,rapid prototyping and complex validation workflows.

---

## Improvisation that project needs in the future

1. **BCrypt password hashing** — Hash passwords before storing using `BCryptPasswordEncoder`, and use `passwordEncoder.matches()` at login instead of `.equals()`.Removes the possibility of password leak and makes it more secure entity.

2. **Extract userId from JWT in all endpoints** — Currently dashboard and record endpoints accept user ID as a path variable. Extracting it from the JWT token directly would help in preventing any authenticated user from accessing another user's data by guessing their ID

3. **Pagination on list endpoints** — `GET /users` and `GET /records/id/{id}` currently load all records at once. This is a good design for small datasets but at production level Adding `Pageable` support would make these endpoints production-safe for large datasets.

4. **Refresh token system** — Currently users are logged out after 1 hour when the JWT expires. A refresh token mechanism would silently issue a new token without requiring re-login

6. **Unit tests for service layer** — Right now inside the project we are using the inserted data from database for various testing but it can create issues in data alteration,so Adding JUnit tests for `UserService`, `FinancialRecordService`, and `DashboardService` will be beneficial in future to verify business logic independently of the database.

---

## Author

**Prashant Surve**
prashantvssurve@gmail.com







