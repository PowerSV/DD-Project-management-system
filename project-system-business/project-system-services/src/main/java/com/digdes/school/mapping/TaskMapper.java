package com.digdes.school.mapping;

import com.digdes.school.dto.member.MemberDTO;
import com.digdes.school.dto.task.CreateTaskDTO;
import com.digdes.school.dto.task.TaskDTO;
import com.digdes.school.models.Member;
import com.digdes.school.models.Task;
import com.digdes.school.models.statuses.TaskStatus;
import com.digdes.school.repos.JpaRepos.MemberJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class TaskMapper {

    private final MemberJpaRepository memberRepository;
    private final MemberMapper memberMapper;

    public TaskMapper(MemberJpaRepository memberRepository, MemberMapper memberMapper) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }

    public Task create(CreateTaskDTO dto) {
        Task task = new Task();
        task.setName(dto.getName());
        task.setDescription(dto.getDescription());

        if (dto.getAssignee() != null) {
            task.setAssignee(memberRepository.findById(dto.getAssignee().getId()).orElse(null));
        }
        task.setComplexity(dto.getComplexity());

        Calendar calendar = Calendar.getInstance();
        task.setCreationDate(calendar.getTime());
        task.setLastModified(calendar.getTime());
        calendar.add(Calendar.HOUR_OF_DAY, dto.getComplexity());
        Date minDeadline = calendar.getTime();
        if (dto.getDeadline().before(minDeadline)) {
            throw new RuntimeException("You cannot select a date if the date is less than the creation date + complexity");
        }
        task.setDeadline(dto.getDeadline());
        task.setStatus(TaskStatus.NEW);

        if (dto.getAuthor() != null) {
            task.setAuthor(memberRepository.findById(dto.getAuthor().getId()).orElse(null));
        }
        return task;
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
