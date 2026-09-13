package keenay.education.service;

import keenay.education.dto.tasks.TaskBodyDTO;
import keenay.education.dto.tasks.TaskDTO;
import keenay.education.security.CustomUserDetail;

import java.util.List;

public interface TaskService {
    TaskDTO createTask(CustomUserDetail customUserDetail, TaskBodyDTO taskBodyDTO);
    TaskDTO getTask(CustomUserDetail customUserDetail, Long id);
    List<TaskDTO> getAvailTasks(CustomUserDetail customUserDetail);
    TaskDTO updateTask(CustomUserDetail customUserDetail, Long id, TaskBodyDTO taskBodyDTO);
    void deleteTask(CustomUserDetail customUserDetail, Long id);
}
