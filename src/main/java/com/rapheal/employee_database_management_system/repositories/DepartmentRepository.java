package com.rapheal.employee_database_management_system.repositories;

import com.rapheal.employee_database_management_system.entites.Department;
import org.springframework.data.jpa.repository.JpaRepository;


// Department repository for saving department entities
public interface DepartmentRepository extends JpaRepository<Department,Long> {

}
