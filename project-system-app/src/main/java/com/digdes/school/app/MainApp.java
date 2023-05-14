package com.digdes.school.app;

import com.digdes.school.controllers.MemberController;
import com.digdes.school.dto.member.CreateUpdateMemberDTO;
import com.digdes.school.dto.member.MemberDTO;

public class MainApp {

    private static final MemberController MEMBER_CONTROLLER = new MemberController();

    public static void main(String... args){
        CreateUpdateMemberDTO newMember1 = new CreateUpdateMemberDTO();
        newMember1.setFirstName("Иван");
        newMember1.setMiddleName("Федорович");
        MemberDTO createdMember1 = MEMBER_CONTROLLER.create(newMember1);
        System.out.println("===== Created member 1========");
        System.out.println(createdMember1);
        System.out.println("===== File content ========");
        System.out.println(MEMBER_CONTROLLER.getAll());
        System.out.println();

        CreateUpdateMemberDTO newMember2 = new CreateUpdateMemberDTO();
        newMember2.setFirstName("Дима");
        newMember2.setMiddleName("Какойтович");
        System.out.println("===== Created member 2 ========");
        MemberDTO createdMember2 = MEMBER_CONTROLLER.create(newMember2);
        System.out.println(createdMember2);
        System.out.println("===== File content ========");
        System.out.println(MEMBER_CONTROLLER.getAll());
        System.out.println();

        newMember2.setMiddleName("Викторович");
        System.out.println("===== updated member 2 ========");
        MemberDTO updatedMember2 = MEMBER_CONTROLLER.update(newMember2, 2L);
        System.out.println(updatedMember2);
        System.out.println("===== File content ========");
        System.out.println(MEMBER_CONTROLLER.getAll());
        System.out.println();

        MemberDTO selectedMember = MEMBER_CONTROLLER.getById(1L);
        System.out.println("===== selected member ========");
        System.out.println(selectedMember);
        System.out.println("===== File content  ========");
        System.out.println(MEMBER_CONTROLLER.getAll());
        System.out.println();

        MemberDTO deletedMember = MEMBER_CONTROLLER.deleteById(2L);
        System.out.println("===== deleted member ========");
        System.out.println(deletedMember);
        System.out.println("===== File content  ========");
        System.out.println(MEMBER_CONTROLLER.getAll());
        System.out.println();
    }
}
