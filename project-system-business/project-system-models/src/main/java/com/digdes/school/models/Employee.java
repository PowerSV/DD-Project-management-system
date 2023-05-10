package com.digdes.school.models;

import com.digdes.school.models.statuses.EmployeeStatus;

public class Employee {

    private Long id;
    private String firstName;
    private String lastName;
    private EmployeeStatus employeeStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public EmployeeStatus getStatus() {
        return employeeStatus;
    }

    public void setStatus(EmployeeStatus employeeStatus) {
        this.employeeStatus = employeeStatus;
    }
}
