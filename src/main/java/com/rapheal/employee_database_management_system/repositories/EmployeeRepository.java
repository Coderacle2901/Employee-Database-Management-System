package com.rapheal.employee_database_management_system.repositories;

import com.rapheal.employee_database_management_system.entites.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
