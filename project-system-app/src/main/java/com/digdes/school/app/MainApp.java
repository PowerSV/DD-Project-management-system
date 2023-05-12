package com.digdes.school.app;

import com.digdes.school.controllers.MemberController;
import com.digdes.school.dto.member.CreateMemberDTO;
import com.digdes.school.dto.member.MemberDTO;

import java.util.List;

public class MainApp {

    private static final MemberController MEMBER_CONTROLLER = new MemberController();

    public static void main(String... args){
        CreateMemberDTO newEmployee1 = new CreateMemberDTO();
        newEmployee1.setFirstName("Иван");
        newEmployee1.setFirstName("Федорович");
        MemberDTO createdEmployee1 = MEMBER_CONTROLLER.create(newEmployee1);
        System.out.println("===== Created employee 1========");
        System.out.println(createdEmployee1);


        CreateMemberDTO newEmployee2 = new CreateMemberDTO();
        newEmployee2.setFirstName("Дима");
        newEmployee2.setFirstName("Какой");

        System.out.println("===== Created employee 2 ========");
        MemberDTO createdEmployee2 = MEMBER_CONTROLLER.create(newEmployee2);
        System.out.println(createdEmployee2);

        System.out.println("======== All employee =========");
        List<MemberDTO> allEmployees = MEMBER_CONTROLLER.getAll();
        System.out.println(allEmployees);

    }
}
