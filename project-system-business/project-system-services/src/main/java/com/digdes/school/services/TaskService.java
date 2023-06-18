package com.digdes.school.services;

import com.digdes.school.dto.task.CreateTaskDTO;
import com.digdes.school.dto.task.TaskDTO;
import com.digdes.school.dto.task.TaskFilter;
import com.digdes.school.dto.task.UpdateTaskDTO;

import java.util.List;

public interface TaskService extends Service<TaskDTO, CreateTaskDTO> {
    List<TaskDTO> search(TaskFilter filter);
    TaskDTO updateStatus(Long id);
    TaskDTO update(UpdateTaskDTO dto);
}
