package keenay.education.service.impl;

import keenay.education.dto.tasks.TaskBodyDTO;
import keenay.education.dto.tasks.TaskDTO;
import keenay.education.security.CustomUserDetail;
import keenay.education.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    @Override
    public TaskDTO createTask(CustomUserDetail customUserDetail, TaskBodyDTO taskBodyDTO) {
        return null;
    }

    @Override
    public TaskDTO getTask(CustomUserDetail customUserDetail, Long id) {
        return null;
    }

    @Override
    public List<TaskDTO> getAvailTasks(CustomUserDetail customUserDetail) {
        return List.of();
    }

    @Override
    public TaskDTO updateTask(CustomUserDetail customUserDetail, Long id, TaskBodyDTO taskBodyDTO) {
        return null;
    }

    @Override
    public void deleteTask(CustomUserDetail customUserDetail, Long id) {

    }
}
