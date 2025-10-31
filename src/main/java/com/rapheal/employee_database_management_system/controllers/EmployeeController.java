package com.rapheal.employee_database_management_system.controllers;

import com.rapheal.employee_database_management_system.DTOs.EmployeeDTO;
import com.rapheal.employee_database_management_system.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;


    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Creates a new employee and saves it to the database.
    // Returns the created employee with status 201 (Created).
    @PostMapping
    public ResponseEntity<EmployeeDTO> addEmployee(@RequestBody @Valid EmployeeDTO employeeDTO){
        EmployeeDTO savedEmployeeDTO = employeeService.createEmployee(employeeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployeeDTO);
    }

    // Gets an employee by their ID.
    // Returns the employee details with status 200 (OK) if found,
    // or 404 (Not Found) if the employee doesn't exist.
    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployeeByID(@PathVariable Long employeeId){
        EmployeeDTO employeeDTO = employeeService.getEmployeeById(employeeId);
        return ResponseEntity.ok(employeeDTO);
    }

    // Gets a list of all employees.
    // Returns status 200 (OK) along with the list of employees.
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(){
        List<EmployeeDTO> employeeDTOList = employeeService.getAllEmployees();
        return ResponseEntity.ok(employeeDTOList);
    }

    // Updates an existing employee's details.
    // Returns the updated employee with status 200 (OK).
    // If the employee is not found, returns 404 (Not Found).
    @PutMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> updateDetails (@PathVariable Long employeeId,@RequestBody @Valid EmployeeDTO newEmployeeDetails){
        EmployeeDTO updatedEmployeeDTO = employeeService.updateEmployeeDetails(employeeId,newEmployeeDetails);
        return ResponseEntity.ok(updatedEmployeeDTO);
    }

    // Partially updates an existing employee's details.
    // Only the provided fields will be updated.
    // Returns the updated employee with status 200 (OK),
    // or 404 (Not Found) if the employee doesn't exist.
    @PatchMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> UpdateDetailPartially(@PathVariable Long employeeId, @RequestBody Map<String,Object> details){
        EmployeeDTO updatedEmployee = employeeService.updateEmployeeDetailsPartially(employeeId,details);
        return ResponseEntity.ok(updatedEmployee);
    }

    // Deletes an employee by their ID.
    // Returns status 204 (No Content) if deleted successfully,
    // or 404 (Not Found) if the employee doesn't exist.
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<?> deleteEmployee (@PathVariable Long employeeId){
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
