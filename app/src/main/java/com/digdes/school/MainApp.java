package com.digdes.school;

import com.digdes.school.employee.CreateEmployeeDTO;
import com.digdes.school.employee.EmployeeDTO;

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


        CreateEmployeeDTO createdEmployee2 = new CreateEmployeeDTO();
        createdEmployee2.setFirstName("Дима");
        createdEmployee2.setFirstName("Какой");

        System.out.println("===== Created employee 2 ========");
        EmployeeDTO createdemployee2 = employeeController.create(createdEmployee2);
        System.out.println(createdemployee2);

        System.out.println("======== All employee =========");
        List<EmployeeDTO> allEmployees = employeeController.getAll();
        System.out.println(allEmployees);

    }
}
