package com.rapheal.employee_database_management_system.controllers;

import com.rapheal.employee_database_management_system.DTOs.DepartmentDTO;
import com.rapheal.employee_database_management_system.DTOs.EmployeeDTO;
import com.rapheal.employee_database_management_system.services.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;
    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    // Creates a new department using the provided DepartmentDTO.
    // Accepts a DepartmentDTO object in the request body.
    // Returns the created DepartmentDTO wrapped in a ResponseEntity
    // with HTTP status 201 (Created) if the operation is successful.
    @PostMapping
    public ResponseEntity<DepartmentDTO> addDepartment(@RequestBody @Valid DepartmentDTO departmentDTO){
        DepartmentDTO newDepartment = departmentService.createDepartment(departmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newDepartment);
    }

    // Retrieves a list of all departments from the database.
    // Returns a list of DepartmentDTO objects wrapped in a ResponseEntity
    // with HTTP status 200 (OK) if the request is successful.
    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getALlDepartments(){
        List<DepartmentDTO> departmentDTOList = departmentService.getAllDepartments();
        return ResponseEntity.ok(departmentDTOList);

    }

    // Retrieves a specific department by its unique ID.
    // Returns the corresponding DepartmentDTO wrapped in a ResponseEntity
    // with HTTP status 200 (OK) if found,
    // or 404 (Not Found) if no department exists with the given ID.
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Long id){
        DepartmentDTO matchingDepartmentDTO = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(matchingDepartmentDTO);
    }

    // Updates the details of an existing department by its ID.
    // Returns the updated DepartmentDTO with HTTP status 200 (OK).
    // Throw an exception if the department is not found.

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDTO> updateDepartmentId(@PathVariable Long id, @RequestBody @Valid DepartmentDTO newDepartmentDetails){
        DepartmentDTO updatedDepartmentDTO = departmentService.updateDepartmentDetails(id,newDepartmentDetails);
        return ResponseEntity.ok(updatedDepartmentDTO);
    }

    // Retrieves all employees belonging to a specific department by its ID.
    // Returns a list of EmployeeDTO objects wrapped in a ResponseEntity with HTTP status 200 (OK).
    // Throws 404 (Not Found) if the department does not exist.
    @GetMapping("/{deptId}/employees")
    public ResponseEntity<List<EmployeeDTO>> getDepartmentEmployees(@PathVariable Long deptId){
        List<EmployeeDTO> employeeDTOList = departmentService.getDepartmentEmployees(deptId);
        return ResponseEntity.ok(employeeDTOList);
    }

    // Deletes a department by its ID.
    // Returns HTTP status 204 (No Content) if the deletion is successful.
    // Throws 404 (Not Found) if no department exists with the given ID.
    @DeleteMapping("/{deptId}")
    public ResponseEntity<?> deleteDepartmentById(@PathVariable Long deptId){
        departmentService.deleteDepartmentById(deptId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
