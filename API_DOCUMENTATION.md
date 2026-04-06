# Finance Dashboard System - API Documentation

## Overview
The Finance Dashboard System is a Spring Boot application that provides a comprehensive REST API for managing financial records and user accounts with role-based access control.

## Base URL
```
http://localhost:8080
```

## Authentication
The system uses JWT (JSON Web Tokens) for authentication. After logging in, include the token in the `Authorization` header:
```
Authorization: Bearer <your_jwt_token>
```

---

## API Endpoints

### 1. Authentication Endpoints

#### 1.1 Login
**Endpoint:** `POST /auth/login`

**Description:** Authenticate a user and receive a JWT token

**Request Body:**
```json
{
  "email": "user@example.com",
  "password": "password123"
}
```

**Response (Success - 200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

**Response (Error - 401 Unauthorized):**
```json
{
  "message": "Invalid credentials"
}
```

**cURL Example:**
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "password123"
  }'
```

---

### 2. User Management Endpoints

#### 2.1 Create User
**Endpoint:** `POST /users`

**Description:** Create a new user account

**Request Body:**
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "securePassword123",
  "roleType": "CLIENT"
}
```

**Available Roles:** `ADMIN`, `ANALYST`, `CLIENT`

**Response (Success - 201 Created):**
```json
{
  "id": 1,
  "username": "John Doe",
  "email": "john@example.com",
  "roleType": "CLIENT"
}
```

**Response (Error - 400 Bad Request):**
```json
{
  "message": "Invalid email or missing required fields"
}
```

**cURL Example:**
```bash
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "password": "securePassword123",
    "roleType": "CLIENT"
  }'
```

---

#### 2.2 Get All Users
**Endpoint:** `GET /users`

**Description:** Retrieve all users (Admin only)

**Response (Success - 200 OK):**
```json
[
  {
    "id": 1,
    "username": "John Doe",
    "email": "john@example.com",
    "roleType": "CLIENT"
  },
  {
    "id": 2,
    "username": "Jane Smith",
    "email": "jane@example.com",
    "roleType": "ANALYST"
  }
]
```

**cURL Example:**
```bash
curl -X GET http://localhost:8080/users \
  -H "Authorization: Bearer <your_jwt_token>"
```

---

#### 2.3 Get User by ID
**Endpoint:** `GET /users/id/{userId}`

**Description:** Retrieve a specific user by ID

**Path Parameters:**
- `userId` (required): The ID of the user

**Response (Success - 200 OK):**
```json
{
  "id": 1,
  "username": "John Doe",
  "email": "john@example.com",
  "roleType": "CLIENT"
}
```

**Response (Error - 404 Not Found):**
```json
{
  "message": "User not found"
}
```

**cURL Example:**
```bash
curl -X GET http://localhost:8080/users/id/1 \
  -H "Authorization: Bearer <your_jwt_token>"
```

---

#### 2.4 Update User
**Endpoint:** `PUT /users/id/{userId}`

**Description:** Update user information

**Path Parameters:**
- `userId` (required): The ID of the user to update

**Request Body:**
```json
{
  "name": "John Updated",
  "email": "johnupdated@example.com",
  "password": "newPassword123",
  "roleType": "CLIENT"
}
```

**Response (Success - 200 OK):**
```json
{
  "id": 1,
  "username": "John Updated",
  "email": "johnupdated@example.com",
  "roleType": "CLIENT"
}
```

**cURL Example:**
```bash
curl -X PUT http://localhost:8080/users/id/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <your_jwt_token>" \
  -d '{
    "name": "John Updated",
    "email": "johnupdated@example.com",
    "password": "newPassword123",
    "roleType": "CLIENT"
  }'
```

---

#### 2.5 Delete User
**Endpoint:** `DELETE /users/id/{userId}`

**Description:** Delete a user account

**Path Parameters:**
- `userId` (required): The ID of the user to delete

**Response (Success - 200 OK):**
```json
{
  "id": 1,
  "username": "John Doe",
  "email": "john@example.com",
  "roleType": "CLIENT"
}
```

**Response (Error - 204 No Content):**
```
Empty body
```

**cURL Example:**
```bash
curl -X DELETE http://localhost:8080/users/id/1 \
  -H "Authorization: Bearer <your_jwt_token>"
```

---

### 3. Financial Records Endpoints

#### 3.1 Create Financial Record
**Endpoint:** `POST /records`

**Description:** Create a new financial record (Income or Expense)

**Headers Required:**
- `Authorization: Bearer <your_jwt_token>` (User ID extracted from token)

**Request Body:**
```json
{
  "amount": 5000.50,
  "description": "Monthly salary",
  "recordType": "INCOME",
  "category": "Salary",
  "date": "2024-04-06T10:30:00"
}
```

**Available Record Types:** `INCOME`, `EXPENSE`

**Response (Success - 200 OK):**
```json
{
  "id": 1,
  "amount": 5000.50,
  "description": "Monthly salary",
  "recordType": "INCOME",
  "category": "Salary",
  "date": "2024-04-06T10:30:00"
}
```

**Response (Error - 400 Bad Request):**
```json
{
  "message": "Please insert a positive amount"
}
```

**cURL Example:**
```bash
curl -X POST http://localhost:8080/records \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <your_jwt_token>" \
  -d '{
    "amount": 5000.50,
    "description": "Monthly salary",
    "recordType": "INCOME",
    "category": "Salary",
    "date": "2024-04-06T10:30:00"
  }'
```

---

#### 3.2 Get Records by User ID
**Endpoint:** `GET /records/id/{id}`

**Description:** Retrieve all financial records for a specific user

**Path Parameters:**
- `id` (required): The user ID

**Response (Success - 200 OK):**
```json
[
  {
    "id": 1,
    "amount": 5000.50,
    "description": "Monthly salary",
    "recordType": "INCOME",
    "category": "Salary",
    "date": "2024-04-06T10:30:00"
  },
  {
    "id": 2,
    "amount": 200.00,
    "description": "Grocery shopping",
    "recordType": "EXPENSE",
    "category": "Food",
    "date": "2024-04-05T15:45:00"
  }
]
```

**cURL Example:**
```bash
curl -X GET http://localhost:8080/records/id/1 \
  -H "Authorization: Bearer <your_jwt_token>"
```

---

#### 3.3 Update Financial Record
**Endpoint:** `PUT /records/id/{id}`

**Description:** Update an existing financial record

**Path Parameters:**
- `id` (required): The record ID

**Request Body:**
```json
{
  "amount": 5500.00,
  "description": "Updated monthly salary",
  "recordType": "INCOME",
  "category": "Salary",
  "date": "2024-04-06T10:30:00"
}
```

**Response (Success - 200 OK):**
```json
{
  "id": 1,
  "amount": 5500.00,
  "description": "Updated monthly salary",
  "recordType": "INCOME",
  "category": "Salary",
  "date": "2024-04-06T10:30:00"
}
```

**cURL Example:**
```bash
curl -X PUT http://localhost:8080/records/id/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <your_jwt_token>" \
  -d '{
    "amount": 5500.00,
    "description": "Updated monthly salary",
    "recordType": "INCOME",
    "category": "Salary",
    "date": "2024-04-06T10:30:00"
  }'
```

---

#### 3.4 Delete Financial Record
**Endpoint:** `DELETE /records/id/{id}`

**Description:** Delete a financial record

**Path Parameters:**
- `id` (required): The record ID

**Response (Success - 200 OK):**
```json
{
  "id": 1,
  "amount": 5000.50,
  "description": "Monthly salary",
  "recordType": "INCOME",
  "category": "Salary",
  "date": "2024-04-06T10:30:00"
}
```

**Response (Error - 204 No Content):**
```
Empty body
```

**cURL Example:**
```bash
curl -X DELETE http://localhost:8080/records/id/1 \
  -H "Authorization: Bearer <your_jwt_token>"
```

---

### 4. Dashboard Endpoints

#### 4.1 Get Dashboard Summary
**Endpoint:** `GET /dashboard/id/{id}`

**Description:** Get financial dashboard summary with total income, expense, and net balance

**Path Parameters:**
- `id` (required): The user ID

**Response (Success - 200 OK):**
```json
{
  "totalIncome": 10000.00,
  "totalExpense": 2500.75,
  "netBalance": 7499.25
}
```

**cURL Example:**
```bash
curl -X GET http://localhost:8080/dashboard/id/1 \
  -H "Authorization: Bearer <your_jwt_token>"
```

---

### 5. Insights Endpoints

#### 5.1 Get Insights
**Endpoint:** `GET /insights`

**Description:** Get financial insights (Available for ANALYST and ADMIN roles)

**Response (Success - 200 OK):**
```json
"Insights data (ANALYST + ADMIN)"
```

**cURL Example:**
```bash
curl -X GET http://localhost:8080/insights \
  -H "Authorization: Bearer <your_jwt_token>"
```

---

### 6. Admin Endpoints

#### 6.1 Admin Create
**Endpoint:** `POST /admin/create`

**Description:** Admin operation to create resources

**Response (Success - 200 OK):**
```json
"Created by admin"
```

**cURL Example:**
```bash
curl -X POST http://localhost:8080/admin/create \
  -H "Authorization: Bearer <admin_jwt_token>"
```

---

#### 6.2 Admin Delete by ID
**Endpoint:** `DELETE /admin/delete/id/{id}`

**Description:** Admin operation to delete resources by ID

**Path Parameters:**
- `id` (required): The resource ID

**Response (Success - 200 OK):**
```json
"Deleted by admin"
```

**cURL Example:**
```bash
curl -X DELETE http://localhost:8080/admin/delete/id/1 \
  -H "Authorization: Bearer <admin_jwt_token>"
```

---

## Error Responses

### Common Error Codes

**400 Bad Request:**
```json
{
  "message": "Invalid request data",
  "errors": {
    "field_name": "Field validation error"
  }
}
```

**401 Unauthorized:**
```json
{
  "message": "Invalid or missing authentication token"
}
```

**403 Forbidden:**
```json
{
  "message": "Access denied. Insufficient permissions."
}
```

**404 Not Found:**
```json
{
  "message": "Resource not found"
}
```

**500 Internal Server Error:**
```json
{
  "message": "An internal server error occurred"
}
```

---

## Data Types & Enumerations

### RecordType
- `INCOME` - For income transactions
- `EXPENSE` - For expense transactions

### RoleType
- `ADMIN` - Administrator with full access
- `ANALYST` - Analyst with insights access
- `CLIENT` - Regular client user

---

## Authentication Flow

1. **Register a new user** using the Create User endpoint
2. **Login** using the Login endpoint to get a JWT token
3. **Include the token** in all subsequent requests via the `Authorization` header
4. **Use the token** for all protected endpoints

---

## Example Workflow

### Step 1: Create a User
```bash
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "password": "securePass123",
    "roleType": "CLIENT"
  }'
```

### Step 2: Login
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "securePass123"
  }'
```
Save the returned token: `eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...`

### Step 3: Create a Financial Record
```bash
curl -X POST http://localhost:8080/records \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
  -d '{
    "amount": 5000.50,
    "description": "Monthly salary",
    "recordType": "INCOME",
    "category": "Salary",
    "date": "2024-04-06T10:30:00"
  }'
```

### Step 4: Get Dashboard Summary
```bash
curl -X GET http://localhost:8080/dashboard/id/1 \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

---

## Rate Limiting
Currently, there is no rate limiting implemented. Please be mindful of server resources.

---

## Versioning
Current API Version: v1.0.0

---

## Support
For issues or questions regarding the API, please contact the development team.

---

## Last Updated
April 6, 2024

