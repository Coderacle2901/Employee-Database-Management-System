package com.rapheal.employee_database_management_system.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DepartmentDTO {
 @NotBlank(message = "A department name is required")
private String departmentName;

 @NotBlank(message = "Email is required")
 @Email(message = "You need to add a valid email address")
 private  String email;

}
