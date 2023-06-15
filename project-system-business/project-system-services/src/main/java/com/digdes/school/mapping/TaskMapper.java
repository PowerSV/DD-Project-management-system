package com.digdes.school.mapping;

import com.digdes.school.dto.member.MemberRoleDTO;
import com.digdes.school.dto.task.CreateTaskDTO;
import com.digdes.school.dto.task.TaskDTO;
import com.digdes.school.models.Task;
import com.digdes.school.models.TeamMember;
import com.digdes.school.models.statuses.TaskStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class TaskMapper {

    private final MemberMapper memberMapper;

    public Task create(CreateTaskDTO dto) {
        Task task = new Task();
        task.setName(dto.getName());
        task.setDescription(dto.getDescription());

        task.setComplexity(dto.getComplexity());

        Calendar calendar = Calendar.getInstance();
        task.setCreationDate(calendar.getTime());
        task.setLastModified(calendar.getTime());
        if (!isDeadlineAfterCreationDate(dto.getDeadline(), task.getCreationDate(), task.getComplexity())) {
            throw new IllegalArgumentException(
                    "Нельзя поставить дедлайн, если complexity + creationDate натсупает позже чем дедлайн");
        }
        task.setDeadline(dto.getDeadline());
        task.setStatus(TaskStatus.NEW);

        return task;
    }

    public boolean isDeadlineAfterCreationDate(Date deadline, Date creationDate, int complexity) {
        Calendar deadlineDate = Calendar.getInstance();
        deadlineDate.setTime(deadline);

        Calendar minDeadlineDate = Calendar.getInstance();
        minDeadlineDate.setTime(creationDate);
        minDeadlineDate.add(Calendar.HOUR_OF_DAY, complexity);

        return deadlineDate.after(minDeadlineDate);
    }

    public TaskDTO map(Task task) {
        TeamMember assignee = task.getAssignee();
        TeamMember author = task.getAuthor();
        MemberRoleDTO assigneeDTO = assignee == null ? null : memberMapper.mapToMemberRoleDTO(assignee);
        MemberRoleDTO authorDTO = author == null ? null : memberMapper.mapToMemberRoleDTO(author);
        return new TaskDTO(
                task.getId(),
                task.getName(),
                task.getDescription(),
                task.getComplexity(),
                task.getCreationDate(),
                task.getLastModified(),
                task.getDeadline(),
                task.getStatus().toString(),
                assigneeDTO,
                authorDTO
        );
    }

}
