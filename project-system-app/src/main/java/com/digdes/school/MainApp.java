package com.digdes.school;

import com.digdes.school.dto.member.CreateUpdateMemberDTO;
import com.digdes.school.dto.member.MemberDTO;
import com.digdes.school.services.MemberService;
import com.digdes.school.services.impl.MemberServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(scanBasePackages = "com.digdes.school")
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

        CreateUpdateMemberDTO newMember3 = new CreateUpdateMemberDTO(
                101L, "Петр", "Петров", "Петрович",
                "DEVELOPER", "petya@gmail.ru"
        );

        MemberDTO createdMember1 = memberService.create(newMember1);
        MemberDTO createdMember2 = memberService.create(newMember2);
        MemberDTO createdMember3 = memberService.create(newMember3);

        System.out.println("======== Created members: ");
        System.out.println(createdMember1 + "\n");
        System.out.println(createdMember2 + "\n");
        System.out.println(createdMember3 + "\n");


        newMember1.setEmail("iiiiiivan@yandex.ru");
        newMember1.setId(createdMember1.getId());
        MemberDTO updatedMember = memberService.update(newMember1);
        System.out.println("======== Updated members: ");
        System.out.println(updatedMember + "\n");

        System.out.println("======== All members: ");
        memberService.getAll().forEach(System.out::println);

        MemberDTO deletedMember = memberService.delete(createdMember3.getId());
        System.out.println("\n" + deletedMember + "\n");

        System.out.println("======== All members: ");
        memberService.getAll().forEach(System.out::println);

    }
}
