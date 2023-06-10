package com.digdes.school.services;

import com.digdes.school.dto.task.CreateTaskDTO;
import com.digdes.school.dto.task.TaskDTO;

import java.util.List;

public interface TaskService extends Service<TaskDTO, CreateTaskDTO> {
    List<TaskDTO> search();
    TaskDTO updateStatus(Long id);
}
