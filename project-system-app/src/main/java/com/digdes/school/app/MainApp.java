package com.digdes.school.app;

import com.digdes.school.dto.member.CreateUpdateMemberDTO;
import com.digdes.school.services.MemberService;
import com.digdes.school.services.impl.MemberServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = "com.digdes.school")
@ComponentScan(basePackages = "com.digdes.school")
public class MainApp {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MainApp.class, args);

        MemberService memberService = context.getBean(MemberServiceImpl.class);

        CreateUpdateMemberDTO newMember1 = new CreateUpdateMemberDTO(
                100L, "Иван", "Иванов", "Иванович",
                "TESTER", "ivan@mail.ru"
        );

        CreateUpdateMemberDTO newMember2 = new CreateUpdateMemberDTO(
                101L, "Василий", "Васильев", "Васильевич",
                "DEVELOPER", "vasya@gmail.ru"
        );

        memberService.create(newMember1);
        memberService.create(newMember2);

    }

//    private static final MemberController MEMBER_CONTROLLER = new MemberController();
//
//    public static void main(String... args){
//        CreateUpdateMemberDTO newMember1 = new CreateUpdateMemberDTO();
//        newMember1.setFirstName("Иван");
//        newMember1.setLastName("Федоров");
//        MemberDTO createdMember1 = MEMBER_CONTROLLER.create(newMember1);
//        System.out.println("===== Created member 1========");
//        System.out.println(createdMember1);
//        System.out.println("===== Storage content ========");
//        System.out.println(MEMBER_CONTROLLER.getAll());
//        System.out.println();
//
//        CreateUpdateMemberDTO newMember2 = new CreateUpdateMemberDTO();
//        newMember2.setFirstName("Дима");
//        newMember2.setLastName("Какойтов");
//        System.out.println("===== Created member 2 ========");
//        MemberDTO createdMember2 = MEMBER_CONTROLLER.create(newMember2);
//        System.out.println(createdMember2);
//        System.out.println("===== Storage content ========");
//        System.out.println(MEMBER_CONTROLLER.getAll());
//        System.out.println();
//
//        newMember2.setLastName("Викторов");
//        newMember2.setMiddleName("Викторович");
//        newMember2.setId(createdMember2.getId());
//        System.out.println("===== updated member 2 ========");
//        MemberDTO updatedMember2 = MEMBER_CONTROLLER.update(newMember2);
//        System.out.println(updatedMember2);
//        System.out.println("===== Storage content ========");
//        System.out.println(MEMBER_CONTROLLER.getAll());
//        System.out.println();
//
//        MemberDTO selectedMember = MEMBER_CONTROLLER.getById(1L);
//        System.out.println("===== selected member ========");
//        System.out.println(selectedMember);
//        System.out.println("===== Storage content  ========");
//        System.out.println(MEMBER_CONTROLLER.getAll());
//        System.out.println();
//
//        MemberDTO deletedMember = MEMBER_CONTROLLER.deleteById(createdMember2.getId());
//        System.out.println("===== deleted member ========");
//        System.out.println(deletedMember);
//        System.out.println("===== Storage content  ========");
//        System.out.println(MEMBER_CONTROLLER.getAll());
//        System.out.println();
//
//
//    }
}
