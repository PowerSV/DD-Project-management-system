package com.digdes.school.app;

import com.digdes.school.controllers.EmployeeController;
import com.digdes.school.dto.employee.CreateEmployeeDTO;
import com.digdes.school.dto.employee.EmployeeDTO;

import java.util.List;

public class MainApp {

    private static final EmployeeController employeeController = new EmployeeController();

    public static void main(String... args){
        CreateEmployeeDTO newEmployee1 = new CreateEmployeeDTO();
        newEmployee1.setFirstName("Иван");
        newEmployee1.setFirstName("Федорович");
        EmployeeDTO createdEmployee1 = employeeController.create(newEmployee1);
        System.out.println("===== Created employee 1========");
        System.out.println(createdEmployee1);


        CreateEmployeeDTO newEmployee2 = new CreateEmployeeDTO();
        newEmployee2.setFirstName("Дима");
        newEmployee2.setFirstName("Какой");

        System.out.println("===== Created employee 2 ========");
        EmployeeDTO createdEmployee2 = employeeController.create(newEmployee2);
        System.out.println(createdEmployee2);

        System.out.println("======== All employee =========");
        List<EmployeeDTO> allEmployees = employeeController.getAll();
        System.out.println(allEmployees);

    }
}
