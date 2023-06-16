package com.digdes.school.task;

import com.digdes.school.dto.member.MemberDTO;
import com.digdes.school.dto.task.CreateTaskDTO;
import com.digdes.school.dto.task.TaskDTO;
import com.digdes.school.dto.task.UpdateTaskDTO;
import com.digdes.school.models.*;
import com.digdes.school.models.statuses.MemberRole;
import com.digdes.school.models.statuses.ProjectStatus;
import com.digdes.school.repos.JpaRepos.*;
import com.digdes.school.services.MemberDetailsService;
import com.digdes.school.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Collections;
import java.util.NoSuchElementException;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskUpdateTest {

    private final TaskService underTest;
    private final TeamMemberJpaRepository teamMemberRepository;
    private final TeamJpaRepository teamRepository;
    private final MemberJpaRepository memberRepository;
    private final MemberDetailsService memberDetailsService;
    private final ProjectJpaRepository projectRepository;
    private final TaskJpaRepository taskRepository;

    private Project testProject;
    private TeamMember testTeamMember;
    private Team testTeam;
    private TaskDTO taskToUpdate;

    @BeforeAll
    public void init() {
        //создание тестового проекта
        testProject = new Project();
        testProject.setName("Test project");
        testProject.setProjectStatus(ProjectStatus.DRAFT);

        // Создание команды и сотрудника в ней
        MemberDetails memberDetails = (MemberDetails) memberDetailsService.loadUserByUsername("test@mail.ru");
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                memberDetails,
                null,
                memberDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Member testMember = memberRepository.findByEmail("test@mail.ru").orElseThrow();
        testTeam = new Team();
        testTeamMember = TeamMember.builder()
                .team(testTeam)
                .member(testMember)
                .role(MemberRole.TESTER)
                .build();

        testTeam.setTeamMembers(Collections.singletonList(testTeamMember));
        testProject.setTeam(testTeam);
        testTeam.setProject(testProject);

        projectRepository.save(testProject);
        teamRepository.save(testTeam);
        teamMemberRepository.save(testTeamMember);

        // создание задачи для последуюющего ее обновления
        CreateTaskDTO testTaskDTO = new CreateTaskDTO();
        testTaskDTO.setName("Название задачи");

        MemberDTO testMemberDto = new MemberDTO();
        testMemberDto.setId(testTeamMember.getMember().getId());
        testTaskDTO.setAssignee(testMemberDto);
        testTaskDTO.setProjectId(testProject.getId());
        testTaskDTO.setComplexity(5);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2024);
        testTaskDTO.setDeadline(calendar.getTime());

        taskToUpdate = underTest.create(testTaskDTO);
    }

    @AfterAll
    public void clearInitChanges() {
        taskRepository.deleteById(taskToUpdate.getId());
        teamRepository.delete(testTeam);
        projectRepository.delete(testProject);
    }

    /**
     * Обновление несуществеющего проекта
     */
    @Test
    public void updateNotExistTask() {
        UpdateTaskDTO updateTaskDTO = new UpdateTaskDTO();
        updateTaskDTO.setId(-1L);

        Assertions.assertThatThrownBy(() -> underTest.update(updateTaskDTO))
                .isInstanceOf(NoSuchElementException.class);
    }
}
