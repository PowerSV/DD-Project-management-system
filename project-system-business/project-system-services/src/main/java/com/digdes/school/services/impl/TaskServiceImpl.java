package com.digdes.school.services.impl;

import com.digdes.school.dto.member.MemberDTO;
import com.digdes.school.dto.task.CreateTaskDTO;
import com.digdes.school.dto.task.TaskDTO;
import com.digdes.school.dto.task.TaskFilter;
import com.digdes.school.dto.task.UpdateTaskDTO;
import com.digdes.school.mapping.TaskMapper;
import com.digdes.school.models.*;
import com.digdes.school.models.statuses.MemberStatus;
import com.digdes.school.models.statuses.TaskStatus;
import com.digdes.school.repos.JpaRepos.MemberJpaRepository;
import com.digdes.school.repos.JpaRepos.ProjectJpaRepository;
import com.digdes.school.repos.JpaRepos.TaskJpaRepository;
import com.digdes.school.repos.specifications.TaskSpecification;
import com.digdes.school.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskJpaRepository taskRepository;
    private final MemberJpaRepository memberRepository;
    private final ProjectJpaRepository projectRepository;
    private final TaskMapper taskMapper;

    @Override
    public TaskDTO get(Long id) {
        return taskRepository.findById(id)
                .map(taskMapper::map)
                .orElseThrow();
    }

    @Override
    public List<TaskDTO> getAll() {
        return taskRepository.findAll().stream()
                .map(taskMapper::map)
                .toList();
    }

    @Override
    public TaskDTO create(CreateTaskDTO dto) {
        Project project = projectRepository.findById(dto.getProjectId()).orElseThrow();
        Member author = getAuthor();
        TeamMember authorTeamMember = getMemberInProjectTeam(author, project);
        Task newTask = taskMapper.create(dto);
        newTask.setAuthor(authorTeamMember);

        setAssigneeOnTask(dto.getAssignee(), newTask);

        newTask = taskRepository.save(newTask);
        return taskMapper.map(newTask);
    }

    @Override
    public TaskDTO update(CreateTaskDTO dto) {
        return null;
    }

    @Override
    public TaskDTO update(UpdateTaskDTO dto) {
        Task task = taskRepository.findById(dto.getId()).orElseThrow();

        Member author = getAuthor();
        TeamMember authorTeamMember = getMemberInProjectTeam(author, task.getAuthor().getTeam().getProject());
        task.setAuthor(authorTeamMember);

        if (!dto.getName().isBlank()) {
            task.setName(dto.getName());
        }
        if (!dto.getDescription().isBlank()) {
            task.setDescription(dto.getDescription());
        }

        setAssigneeOnTask(dto.getAssignee(), task);

        if (dto.getComplexity() != null) {
            task.setComplexity(dto.getComplexity());
        }
        if (dto.getDeadline() != null) {
            if (taskMapper.isDeadlineAfterCreationDate(
                    dto.getDeadline(),
                    task.getCreationDate(),
                    task.getComplexity())) {
                throw new IllegalArgumentException(
                        "Нельзя поставить дедлайн, если complexity + creationDate натсупает позже чем дедлайн");
            }
            task.setDeadline(dto.getDeadline());
        }

        task.setLastModified(Calendar.getInstance().getTime());
        task = taskRepository.save(task);
        return taskMapper.map(task);
    }

    private void setAssigneeOnTask(MemberDTO assignee, Task task) {
        if (assignee != null) {
            Member newAssignee = memberRepository.findById(assignee.getId()).orElseThrow();
            if (isAssigneeDeleted(newAssignee)) {
                throw new IllegalArgumentException("Испольнитель должен иметь статус ACTIVE");
            }
            TeamMember assigneeTeamMember = getMemberInTeam(newAssignee, task.getAuthor().getTeam());
            task.setAssignee(assigneeTeamMember);
        }
    }

    private TeamMember getMemberInProjectTeam(Member member, Project project) {
        return getMemberInTeam(member, project.getTeam());
    }

    private TeamMember getMemberInTeam(Member member, Team team) {
        return team.getTeamMembers()
                .stream()
                .filter(teamMember -> teamMember.getMember().equals(member))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Автором либо исполнителем задачи может являться только участник проекта"));
    }

    public boolean isAssigneeDeleted(Member assignee) {
        return assignee.getStatus() == MemberStatus.DELETED;
    }

    private Member getAuthor() {
        MemberDetails principal = (MemberDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        return principal.getMember();
    }

    @Override
    public List<TaskDTO> search(TaskFilter filter) {
        Specification<Task> spec = TaskSpecification.searchByFilter(filter);
        return taskRepository.findAll(spec)
                .stream()
                .map(taskMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDTO updateStatus(Long id) {
        Task task = taskRepository.findById(id).orElseThrow();
        TaskStatus currentStatus = task.getStatus();
        task.setStatus(getNextStatus(currentStatus));
        task = taskRepository.save(task);
        return taskMapper.map(task);
    }

    private TaskStatus getNextStatus(TaskStatus status) {
        return switch (status) {
            case NEW -> TaskStatus.IN_PROGRESS;
            case IN_PROGRESS -> TaskStatus.DONE;
            case DONE, CLOSED -> TaskStatus.CLOSED;
        };
    }

    @Override
    public TaskDTO deleteFromStorage(Long id) {
        Task deletedTask = taskRepository.findById(id).orElseThrow();
        taskRepository.deleteTaskById(id);
        return taskMapper.map(deletedTask);
    }
}
