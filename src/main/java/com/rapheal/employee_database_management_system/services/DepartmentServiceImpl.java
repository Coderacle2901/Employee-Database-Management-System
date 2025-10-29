package com.rapheal.employee_database_management_system.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rapheal.employee_database_management_system.DTOs.DepartmentDTO;
import com.rapheal.employee_database_management_system.entites.Department;
import com.rapheal.employee_database_management_system.repositories.DepartmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

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
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }


    // Find and returns a particular department as DTO using the department ID
    @Override
    public DepartmentDTO getDepartmentById(Long id) {
       Department  matchingDepartment = departmentRepository.findById(id).orElseThrow(()-> new NoSuchElementException("Department with ID: "+ id + " not found"));

       return mapper.map(matchingDepartment, DepartmentDTO.class);
    }

    // Finds and updates a particular department record in the department repository
    @Override
    public DepartmentDTO updateDepartmentDetails(Long id, DepartmentDTO newDepartmentDetails) {

        Department  matchingDepartment = departmentRepository.findById(id).orElseThrow(()-> new NoSuchElementException("Department with ID: "+ id + " not found"));



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
        Department matchingDepartment = departmentRepository.findById(id).orElseThrow(()-> new NoSuchElementException("Department with ID: " + id + " not found"));

        departmentRepository.delete(matchingDepartment);

    }
}
