package com.rapheal.employee_database_management_system.services;

import com.rapheal.employee_database_management_system.DTOs.EmployeeDTO;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    // Save a new employee to the database.
    EmployeeDTO createEmployee(EmployeeDTO newEmployeeData);

    // Retrieve all employees.
    List<EmployeeDTO> getAllEmployees();

    // Get a specific employee using their ID.
    EmployeeDTO getEmployeeById(Long id);

    // Update an employee’s details.
    EmployeeDTO updateEmployeeDetails(Long id, EmployeeDTO newEmployeeDetails);

    // Update  single/multiple specific employee details.
    EmployeeDTO updateEmployeeDetailsPartially(Long id, Map<String,Object> updateData);

    // Delete an employee from the Database.
    void deleteEmployee(Long id);
}
