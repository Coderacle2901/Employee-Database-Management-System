package com.rapheal.employee_database_management_system.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmployeeDTO {

    @NotBlank(message = "First Name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "You need to enter a valid email address")
    private String email;

    @NotBlank(message = "Age is required")
    @Min(value = 10, message = "Invalid Age: Age must be greater than 10")
    @Max(value = 65, message = "Invalid Age: Age must be below 65")
    private int age;

    @NotBlank(message = "Hire Date is required")
    private String hireDate;
}
