package com.rapheal.employee_database_management_system.services;

import com.rapheal.employee_database_management_system.DTOs.DepartmentDTO;
import com.rapheal.employee_database_management_system.entites.Department;

import java.util.List;

public interface DepartmentService {
    Department createDepartment(DepartmentDTO newDepartmentData);
    List<Department> getAllDepartments();
    Department getDepartmentById(Long id);
    Department updateDepartmentDetails(Long id, DepartmentDTO newDepartmentDetails);
    void deleteDepartmentById(Long id);



}
