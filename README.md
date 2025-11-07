# Employee Database Management System

This is a **backend project I built using Java 17 and Spring Boot 3**. It’s a complete Employee Database Management System that allows managing employees and departments efficiently. I built this project to practice professional REST API development, proper validation, and exception handling, while also creating a portfolio-ready project for potential employers.

---

## Project Overview

The project is a **Spring Boot application** that can:

- Create, read, update, and delete employees and departments  
- Associate employees with departments  
- Validate user input to prevent invalid data  
- Handle exceptions globally and return meaningful error responses  
- Support both full updates (PUT) and partial updates (PATCH)  

I made sure to follow **best practices in REST API design**, so it’s structured and easy to maintain.  

---

## Features

### Employee Management
- Add, update, and delete employees  
- Validate employee input with `jakarta.validation`  
- Update just the email of an employee using PATCH  

### Department Management
- CRUD operations for departments  
- Employees can be associated with departments  
- Update department details using PUT  

### Validation & Exception Handling
- Input validation using annotations like `@NotBlank`, `@NotNull`, `@Email`, `@Min`, and `@Max`  
- Custom exception `ResourceNotFoundException` for missing resources  
- Global exception handler for returning structured JSON errors  

---

## Tech Stack

- **Java 17**  
- **Spring Boot 3**  
- **Database:** H2 (in-memory) / PostgreSQL  
- **Build Tool:** Maven  
- **Validation:** Jakarta Validation  
- **Mapping:** MapStruct  
- **Testing:** Postman  

---

## API Endpoints

### Departments

| Method | Endpoint             | Description                  |
|--------|--------------------|------------------------------|
| GET    | `/departments`       | Get all departments          |
| GET    | `/departments/{id}`  | Get department by ID        |
| POST   | `/departments`       | Create a new department      |
| PUT    | `/departments/{id}`  | Update a department          |
| DELETE | `/departments/{id}`  | Delete a department          |

### Employees

| Method | Endpoint           | Description                     |
|--------|------------------|---------------------------------|
| GET    | `/employees`       | Get all employees               |
| GET    | `/employees/{id}`  | Get employee by ID             |
| POST   | `/employees`       | Create a new employee          |
| PATCH  | `/employees/{id}`  | Update employee email only     |
| DELETE | `/employees/{id}`  | Delete an employee             |

**Example Error Response**:

```json
{
  "error": "Department with ID: 99 not found",
  "timeStamp": "2025-11-07T07:03:16.882797400"
}



