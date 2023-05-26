package com.digdes.school.services;

import com.digdes.school.dto.task.CreateTaskDTO;
import com.digdes.school.dto.task.TaskDTO;

import java.util.List;

public interface TaskService {
    TaskDTO getTask(Long id);

    TaskDTO create(CreateTaskDTO dto);

    TaskDTO update(CreateTaskDTO dto);

    List<TaskDTO> search();

    TaskDTO updateStatus(Long id);
}
