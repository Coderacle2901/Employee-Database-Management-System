package com.rapheal.employee_database_management_system.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rapheal.employee_database_management_system.DTOs.EmployeeDTO;
import com.rapheal.employee_database_management_system.entites.Department;
import com.rapheal.employee_database_management_system.entites.Employee;
import com.rapheal.employee_database_management_system.repositories.DepartmentRepository;
import com.rapheal.employee_database_management_system.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService{
    // Object mapper for easier conversion of DtOs to Maps
    private final ObjectMapper objectMapper;

    // ModelMapper for faster and easier mapping of DtOs to Entities
    private final ModelMapper mapper;

    // Department repository for accessing the Department Database
    private final DepartmentRepository departmentRepository;

    // Employee repository for accessing the Employee Database
    private final EmployeeRepository employeeRepository;

    // Constructor Dependency Injection

    @Autowired
    public EmployeeServiceImpl(ModelMapper mapper, DepartmentRepository departmentRepository, EmployeeRepository employeeRepository, ObjectMapper objectMapper) {
        this.mapper = mapper;
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
        this.objectMapper = objectMapper;
    }

    // Creates and saves a new Employee to the employee database.
@Override
public EmployeeDTO createEmployee(EmployeeDTO newEmployeeData) {

    Employee employee = mapper.map(newEmployeeData,Employee.class);

        Department dept = departmentRepository.findById(newEmployeeData.getDepartmentId()).orElseThrow(()-> new NoSuchElementException("Department not found with ID: " + newEmployeeData.getDepartmentId()));

        dept.addEmployee(employee);
        Employee savedEmployee = employeeRepository.save(employee);

        return mapper.map(savedEmployee,EmployeeDTO.class);

    }


    // Returns a list of all employees in the database.
    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(e -> mapper.map(e,EmployeeDTO.class))
                .collect(Collectors.toList());
    }


    // Finds and returns a specific Employee from the database using an employee ID
    @Override
    public EmployeeDTO getEmployeeById(Long id) {

        Employee matchingEmployee = employeeRepository.findById(id).orElseThrow(()-> new NoSuchElementException("Employed with ID: " + id + " could not be found"));

       return  mapper.map(matchingEmployee,EmployeeDTO.class);
    }


    // Updates an Employee record from an Employee DTO mapped from the Rest api Request body
    @Override
    public EmployeeDTO updateEmployeeDetails(Long id, EmployeeDTO newEmployeeDetails) {

        Employee matchingEmployee = employeeRepository.findById(id).orElseThrow(()-> new NoSuchElementException("Employed with ID: " + id + " could not be found"));

        Map<String, Object> employeeDetails = objectMapper.convertValue(newEmployeeDetails, Map.class);

        for(Map.Entry<String, Object> employee : employeeDetails.entrySet()){
            String key = employee.getKey();
            Object value = employee.getValue();


            // Skip null values

            if(value == null){
                continue;
            }

            switch (key){
                case "firstName" -> matchingEmployee.setFirstName((String) value);
                case "lastName" -> matchingEmployee.setLastName((String) value);
                case "email" -> matchingEmployee.setEmail((String) value);
                case "age" -> matchingEmployee.setAge(Integer.parseInt(value.toString()));
                case "hireDate" -> matchingEmployee.setHireDate((String) value);
            }

        }

        if (newEmployeeDetails.getDepartmentId() != null) {
            Department newDepartment = departmentRepository.findById(newEmployeeDetails.getDepartmentId())
                    .orElseThrow(() -> new NoSuchElementException("Department not found"));

            Department currentDepartment = matchingEmployee.getDepartment();

            if(currentDepartment != null && !currentDepartment.equals(newDepartment)){
               currentDepartment.getEmployees().remove(matchingEmployee);
            }

            newDepartment.addEmployee(matchingEmployee);
        }

        Employee savedEmployee = employeeRepository.save(matchingEmployee);

        return mapper.map(savedEmployee, EmployeeDTO.class);

    }


    // Update specific fields on an Employee Entity
    @Override
    public EmployeeDTO updateEmployeeDetailsPartially(Long id, Map<String, Object> updateData) {

        Employee matchingEmployee = employeeRepository.findById(id).orElseThrow(()-> new NoSuchElementException("Employed with ID: " + id + " could not be found"));

        for(Map.Entry<String, Object> entry : updateData.entrySet()){
            String key = entry.getKey();
            Object value = entry.getValue();

            if(value == null)continue; // Skip null values

            switch(key){
                case "firstName" -> matchingEmployee.setFirstName((String) value);
                case "lastName" -> matchingEmployee.setLastName((String) value);
                case "email" -> matchingEmployee.setEmail((String) value);
                case "age" -> matchingEmployee.setAge(Integer.parseInt(value.toString()));
                case "hireDate" -> matchingEmployee.setHireDate((String) value);
                case "departmentId" -> {
                    Department newDept = departmentRepository.findById(Long.parseLong(value.toString()))
                            .orElseThrow(() -> new NoSuchElementException("Department not found"));

                    Department currentDept = matchingEmployee.getDepartment();
                    if (currentDept != null && !currentDept.equals(newDept)) {
                        currentDept.getEmployees().remove(matchingEmployee);
                    }

                    newDept.addEmployee(matchingEmployee);
                }

            }
        }


        Employee savedEmployee = employeeRepository.save(matchingEmployee);
        return mapper.map(savedEmployee, EmployeeDTO.class);

    }


    // Deletes a specific Employee Entity Using Employee_ID
    @Override
    public void deleteEmployee(Long id) {

        Employee matchingEmployee = employeeRepository.findById(id).orElseThrow(()-> new NoSuchElementException("Employed with ID: " + id + " could not be found"));

        employeeRepository.delete(matchingEmployee);
    }

}
