package com.rapheal.employee_database_management_system.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rapheal.employee_database_management_system.DTOs.DepartmentDTO;
import com.rapheal.employee_database_management_system.DTOs.EmployeeDTO;
import com.rapheal.employee_database_management_system.entites.Department;
import com.rapheal.employee_database_management_system.entites.Employee;
import com.rapheal.employee_database_management_system.exception.ResourceNotFoundException;
import com.rapheal.employee_database_management_system.repositories.DepartmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    // Object mapper for easier conversion of DtOs to Maps
    private final ObjectMapper objectMapper;

    // Department repository for accessing the Department Database
    private final DepartmentRepository departmentRepository;

    // ModelMapper for faster and easier mapping of DtOs to Entities
    private final ModelMapper mapper;



    // Constructor Dependency Injection
    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository, ModelMapper mapper,ObjectMapper objectMapper) {
        this.departmentRepository = departmentRepository;
        this.mapper = mapper;
        this.objectMapper = objectMapper;

    }


    // Creates a new department entity
    @Override
    public DepartmentDTO createDepartment(DepartmentDTO newDepartmentData) {

        Department newDepartment = mapper.map(newDepartmentData, Department.class);
        Department savedDepartment = departmentRepository.save(newDepartment);
        return mapper.map(savedDepartment, DepartmentDTO.class);

    }

    // Returns a list of all department entities in the database
    @Override
    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(d -> mapper.map(d,DepartmentDTO.class))
                .collect(Collectors.toList());

    }


    // Find and returns a particular department as DTO using the department ID
    @Override
    public DepartmentDTO getDepartmentById(Long id) {
       Department  matchingDepartment = departmentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Department with ID: "+ id + " not found"));

       return mapper.map(matchingDepartment, DepartmentDTO.class);
    }

    // Finds and updates a particular department record in the department repository
    @Override
    public DepartmentDTO updateDepartmentDetails(Long id, DepartmentDTO newDepartmentDetails) {

        Department  matchingDepartment = departmentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Department with ID: "+ id + " not found"));



        Map<String, Object> details = objectMapper.convertValue(newDepartmentDetails, Map.class);

        for(Map.Entry<String,Object> detail : details.entrySet()){
            String key = detail.getKey();
            Object value = detail.getValue();

            if(value == null)continue; // skip null values

            switch(key){
                case "departmentName" -> matchingDepartment.setDepartmentName((String) value);
                case "email" -> matchingDepartment.setEmail((String) value);
            }
        }

        Department updatedDepartment = departmentRepository.save(matchingDepartment);

        return mapper.map(updatedDepartment, DepartmentDTO.class);


    }
    // deletes a specific department record from the database using the specified ID
    @Override
    public void deleteDepartmentById(Long id) {
        Department matchingDepartment = departmentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Department with ID: " + id + " not found"));

        departmentRepository.delete(matchingDepartment);

    }

    // Returns all employees in a department by its ID, sorted by employee ID.
    // Throws an exception if the department is not found.
    @Override
    public List<EmployeeDTO> getDepartmentEmployees(Long id) {
        Department matchingDepartment = departmentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Department with id: " + id + " not found"));

        return matchingDepartment.getEmployees()
                .stream()
                .sorted(Comparator.comparing(Employee::getId))
                .map(e -> mapper.map(e,EmployeeDTO.class))
                .collect(Collectors.toList());
    }




}
