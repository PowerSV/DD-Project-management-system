package com.digdes.school.services.impl;

import com.digdes.school.dto.task.CreateTaskDTO;
import com.digdes.school.dto.task.TaskDTO;
import com.digdes.school.mapping.TaskMapper;
import com.digdes.school.models.Task;
import com.digdes.school.models.statuses.TaskStatus;
import com.digdes.school.repos.JpaRepos.MemberJpaRepository;
import com.digdes.school.repos.JpaRepos.TaskJpaRepository;
import com.digdes.school.services.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskJpaRepository taskRepository;
    private final MemberJpaRepository memberRepository;
    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskJpaRepository taskRepository, MemberJpaRepository memberRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.memberRepository = memberRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public TaskDTO getTask(Long id) {
        return taskRepository.findById(id)
                .map(taskMapper::map)
                .orElseThrow();
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
        task.setName(dto.getName());
        task.setDescription(dto.getDescription());
        task.setAuthor(memberRepository.findById(dto.getAuthor().getId()).orElseThrow());
        task.setAssignee(memberRepository.findById(dto.getAssignee().getId()).orElseThrow());
        task.setComplexity(dto.getComplexity());
        task.setLastModified(Calendar.getInstance().getTime());
        task.setDeadline(dto.getDeadline());
        task = taskRepository.save(task);
        return taskMapper.map(task);
    }

    // todo: search
    @Override
    public List<TaskDTO> search() {
        return null;
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
}
