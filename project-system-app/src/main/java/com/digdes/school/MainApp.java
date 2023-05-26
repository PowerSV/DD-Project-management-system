package com.digdes.school;

import com.digdes.school.dto.member.CreateUpdateMemberDTO;
import com.digdes.school.dto.member.MemberDTO;
import com.digdes.school.dto.project.ProjectDTO;
import com.digdes.school.dto.task.CreateTaskDTO;
import com.digdes.school.dto.task.TaskDTO;
import com.digdes.school.dto.team.AddRemoveMemberDTO;
import com.digdes.school.dto.team.TeamDTO;
import com.digdes.school.models.statuses.MemberRole;
import com.digdes.school.services.MemberService;
import com.digdes.school.services.ProjectService;
import com.digdes.school.services.TaskService;
import com.digdes.school.services.TeamService;
import com.digdes.school.services.impl.MemberServiceImpl;
import com.digdes.school.services.impl.ProjectServiceImpl;
import com.digdes.school.services.impl.TaskServiceImpl;
import com.digdes.school.services.impl.TeamServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.*;

@SpringBootApplication(scanBasePackages = "com.digdes.school")
public class MainApp {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MainApp.class, args);

        MemberService memberService = context.getBean(MemberServiceImpl.class);

        CreateUpdateMemberDTO newMember1 = new CreateUpdateMemberDTO(
                100L, "Андрей", "Адреев", "Андреевич",
                "TESTER", "andrey@mail.ru", null
        );

        CreateUpdateMemberDTO newMember2 = new CreateUpdateMemberDTO(
                101L, "Aлександр", "Александров", "Александрович",
                "DEVELOPER", "alexandrrrr@gmail.ru", "alexandr"
        );

        CreateUpdateMemberDTO newMember3 = new CreateUpdateMemberDTO(
                101L, "bvcccx", "xcvxcverv", "sdfsfsdv",
                "DEVELOPER", "plml@gmail.ru", null
        );

        CreateUpdateMemberDTO newMember4 = new CreateUpdateMemberDTO(
                101L, "Fghjkl", "еререр", "еререр",
                "DEVELOPER", "ghjk@gmail.ru", "впвапвап"
        );

        MemberDTO createdMember1 = memberService.create(newMember1);
        MemberDTO createdMember2 = memberService.create(newMember2);
        MemberDTO createdMember3 = memberService.create(newMember3);
        MemberDTO createdMember4 = memberService.create(newMember4);

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

        ProjectService projectService = context.getBean(ProjectServiceImpl.class);
        ProjectDTO newProject1 = new ProjectDTO(100L, "system management", "description1",
                "dasda", null);
        ProjectDTO createdProject1 = projectService.create(newProject1);

        TeamDTO newTeam1 = new TeamDTO();
        Map<MemberDTO, String> teamComposition = new HashMap<>();
        teamComposition.put(createdMember1, "TESTER");
        teamComposition.put(createdMember2, "DEVELOPER");
        newTeam1.setTeamMembership(teamComposition);

        TeamDTO newTeam2 = new TeamDTO();
        Map<MemberDTO, String> teamComposition2 = new HashMap<>();
        MemberDTO createdMember5 = memberService.getMember(2L);
        teamComposition2.put(createdMember4, "TESTER");
        teamComposition2.put(createdMember5, "DEVELOPER");
        newTeam2.setTeamMembership(teamComposition2);

        TeamService teamService = context.getBean(TeamServiceImpl.class);
        TeamDTO createdTeam1 = teamService.create(newTeam1);
        TeamDTO createdTeam2 = teamService.create(newTeam2);

        projectService.setTeam(createdTeam1, createdProject1.getId());

        System.out.println(createdTeam2);
        AddRemoveMemberDTO addMemberDTO = new AddRemoveMemberDTO();
        addMemberDTO.setMemberDTO(memberService.getMember(1L));
        addMemberDTO.setRole(MemberRole.TESTER.toString());
        addMemberDTO.setId(createdTeam2.getId());
        createdTeam2 = teamService.addMember(addMemberDTO);
        System.out.println(createdTeam2);

        System.out.println(createdTeam1);
        AddRemoveMemberDTO removeMemberDTO = new AddRemoveMemberDTO();
        removeMemberDTO.setMemberDTO(memberService.getMember(2L));
        removeMemberDTO.setId(createdTeam1.getId());
        System.out.println(teamService.removeMember(removeMemberDTO));


        TeamDTO newTeam3 = new TeamDTO();
        Map<MemberDTO, String> teamComposition3 = new HashMap<>();
        teamComposition3.put(createdMember1, MemberRole.TESTER.toString());
        teamComposition3.put(createdMember2, MemberRole.TESTER.toString());
//        teamComposition3.put(createdMember3, MemberRole.PROJECT_MANAGER.toString());
        newTeam3.setTeamMembership(teamComposition3);
        ProjectDTO newProject2 = new ProjectDTO(
                200L, "test", "descr", "dfsdfsd", newTeam3
        );
        newProject2 = projectService.create(newProject2);

        System.out.println(newProject2);

        CreateTaskDTO newTask1 = new CreateTaskDTO();
        newTask1.setName("task 3");
        newTask1.setComplexity(10);
        Calendar calendar = new GregorianCalendar(2024, Calendar.JANUARY, 31);
        newTask1.setDeadline(calendar.getTime());

        TaskService taskService = context.getBean(TaskServiceImpl.class);
        TaskDTO createdTask1 = taskService.create(newTask1);
        System.out.println(createdTask1);

        ProjectDTO projectDTO = projectService.updateStatus(newProject2);
    }
}
