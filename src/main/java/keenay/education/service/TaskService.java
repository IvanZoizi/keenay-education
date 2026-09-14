package keenay.education.service;

import keenay.education.dto.tasks.TaskBodyDTO;
import keenay.education.dto.tasks.TaskBodyStatusDTO;
import keenay.education.dto.tasks.TaskDTO;
import keenay.education.entity.Advertisement;
import keenay.education.security.CustomUserDetail;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TaskService {
    TaskDTO createTask(CustomUserDetail customUserDetail, TaskBodyDTO taskBodyDTO, MultipartFile photo);
    TaskDTO getTask(CustomUserDetail customUserDetail, Long id);
    List<TaskDTO> getAvailTasks(CustomUserDetail customUserDetail);
    List<TaskDTO> getCreatedTasks(CustomUserDetail customUserDetail);
    TaskDTO updateTask(CustomUserDetail customUserDetail, Long id, TaskBodyDTO taskBodyDTO);
    TaskDTO updatePhotoTask(CustomUserDetail customUserDetail, Long id, MultipartFile photo);
    TaskDTO updateTaskStatus(CustomUserDetail customUserDetail, Long id, TaskBodyStatusDTO taskBodyStatusDTO);
    TaskDTO setAdvertisement(CustomUserDetail customUserDetail, Long id, Long advertisementId);
    TaskDTO deleteAdvertisement(CustomUserDetail customUserDetail, Long id);
    void deleteTask(CustomUserDetail customUserDetail, Long id);
}
