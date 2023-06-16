package com.digdes.school.services.impl;

import com.digdes.school.dto.task.CreateTaskDTO;
import com.digdes.school.dto.task.TaskDTO;
import com.digdes.school.dto.task.TaskFilter;
import com.digdes.school.mapping.TaskMapper;
import com.digdes.school.models.MemberDetails;
import com.digdes.school.models.Task;
import com.digdes.school.models.statuses.TaskStatus;
import com.digdes.school.repos.JpaRepos.MemberJpaRepository;
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
        Task newTask = taskMapper.create(dto);
        newTask = taskRepository.save(newTask);
        return taskMapper.map(newTask);
    }

    @Override
    public TaskDTO update(CreateTaskDTO dto) {
        Task task = taskRepository.findById(dto.getId()).orElseThrow();
        if (!dto.getName().isBlank()) {
            task.setName(dto.getName());
        }
        if (!dto.getDescription().isBlank()) {
            task.setDescription(dto.getDescription());
        }
        if (dto.getAssignee() != null) {
            task.setAssignee(memberRepository.findById(dto.getAssignee().getId()).orElseThrow());
        }
        if (dto.getComplexity() != null) {
            task.setComplexity(dto.getComplexity());
        }
        if (dto.getDeadline() != null) {
            task.setDeadline(dto.getDeadline());
        }

        MemberDetails principal = (MemberDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        task.setAuthor(principal.getMember());
        task.setLastModified(Calendar.getInstance().getTime());
        task = taskRepository.save(task);
        return taskMapper.map(task);
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

    @Override
    public TaskDTO deleteFromStorage(Long id) {
        Task deletedTask = taskRepository.findById(id).orElseThrow();
        taskRepository.deleteTaskById(id);
        return taskMapper.map(deletedTask);
    }

    private TaskStatus getNextStatus(TaskStatus status) {
        return switch (status) {
            case NEW -> TaskStatus.IN_PROGRESS;
            case IN_PROGRESS -> TaskStatus.DONE;
            case DONE, CLOSED -> TaskStatus.CLOSED;
        };
    }
}
