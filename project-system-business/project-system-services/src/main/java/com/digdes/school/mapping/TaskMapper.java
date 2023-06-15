package com.digdes.school.mapping;

import com.digdes.school.dto.member.MemberDTO;
import com.digdes.school.dto.task.CreateTaskDTO;
import com.digdes.school.dto.task.TaskDTO;
import com.digdes.school.models.Member;
import com.digdes.school.models.MemberDetails;
import com.digdes.school.models.Task;
import com.digdes.school.models.statuses.MemberStatus;
import com.digdes.school.models.statuses.TaskStatus;
import com.digdes.school.repos.JpaRepos.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class TaskMapper {

    private final MemberJpaRepository memberRepository;
    private final MemberMapper memberMapper;


    public Task create(CreateTaskDTO dto) {
        Task task = new Task();
        task.setName(dto.getName());
        task.setDescription(dto.getDescription());

        if (dto.getAssignee() != null) {
            Member assignee = memberRepository.findById(dto.getAssignee().getId()).orElseThrow();
            if (isAssigneeDeleted(assignee)) {
                throw new IllegalArgumentException("Испольнитель должен иметь статус ACTIVE");
            }
            task.setAssignee(assignee);
        }
        task.setComplexity(dto.getComplexity());

        Calendar calendar = Calendar.getInstance();
        task.setCreationDate(calendar.getTime());
        task.setLastModified(calendar.getTime());
        if (!isDeadlineAfterCreationDate(task.getDeadline(), task.getCreationDate(), task.getComplexity())) {
            throw new IllegalArgumentException(
                    "Нельзя поставить дедлайн, если complexity + creationDate натсупает позже чем дедлайн");
        }
        task.setDeadline(dto.getDeadline());
        task.setStatus(TaskStatus.NEW);

        MemberDetails principal = (MemberDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        task.setAuthor(principal.getMember());
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

    public boolean isAssigneeDeleted(Member assignee) {
        return assignee.getStatus() == MemberStatus.DELETED;
    }

    public TaskDTO map(Task task) {
        Member assignee = task.getAssignee();
        Member author = task.getAuthor();
        MemberDTO assigneeDTO = assignee == null ? null : memberMapper.map(assignee);
        MemberDTO authorDTO = author == null ? null : memberMapper.map(author);
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
