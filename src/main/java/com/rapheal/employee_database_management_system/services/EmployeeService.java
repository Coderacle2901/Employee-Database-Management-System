package com.rapheal.employee_database_management_system.services;

import com.rapheal.employee_database_management_system.DTOs.EmployeeDTO;
import com.rapheal.employee_database_management_system.entites.Employee;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    // Save a new employee to the database.
    Employee createEmployee(EmployeeDTO newEmployeeData);

    // Retrieve all employees.
    List<Employee> getAllEmployees();

    // Get a specific employee using their ID.
    Employee getEmployeeById(Long id);

    // Update an employee’s details.
    Employee updateEmployeeDetails(Long id, EmployeeDTO newEmployeeDetails);

    // Update  single/multiple specific employee details.
    Employee updateEmployeeDetailsPartially(Long id, Map<String,Object> updateData);

    // Delete an employee from the Database.
    void deleteEmployee(Long id);
}
