package com.rapheal.employee_database_management_system.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


// Employee entities blueprint
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private int age;

    private String hireDate;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

}
