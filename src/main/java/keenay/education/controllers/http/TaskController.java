package keenay.education.controllers.http;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import keenay.education.dto.tasks.TaskBodyDTO;
import keenay.education.dto.tasks.TaskBodyStatusDTO;
import keenay.education.dto.tasks.TaskDTO;
import keenay.education.security.CustomUserDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Tasks Endpoints")
@RequestMapping("/api/v1/tasks")
public interface TaskController {
    ResponseEntity<TaskDTO> createTask(
            @AuthenticationPrincipal CustomUserDetail customUserDetail,
            @Valid @RequestBody TaskBodyDTO taskBodyDTO,
            @RequestParam("file") MultipartFile photo);

    ResponseEntity<TaskDTO> getTask(
            @AuthenticationPrincipal CustomUserDetail customUserDetail,
            @PathVariable("id")  Long id);

    ResponseEntity<List<TaskDTO>> getAvailTasks(
            @AuthenticationPrincipal CustomUserDetail customUserDetail);

    ResponseEntity<List<TaskDTO>> getCreatedTasks(
            @AuthenticationPrincipal CustomUserDetail customUserDetail);

    ResponseEntity<TaskDTO> updateTask(
            @AuthenticationPrincipal CustomUserDetail customUserDetail,
            @PathVariable("id") Long id,
            @Valid @RequestBody TaskBodyDTO taskBodyDTO);

    ResponseEntity<TaskDTO> updatePhotoTask(
            @AuthenticationPrincipal CustomUserDetail customUserDetail,
            @PathVariable("id") Long id,
            @RequestParam("file") MultipartFile photo);

    ResponseEntity<TaskDTO> updateTaskStatus(
            @AuthenticationPrincipal CustomUserDetail customUserDetail,
            @PathVariable("id") Long id,
            @Valid @RequestBody TaskBodyStatusDTO taskBodyStatusDTO);

    ResponseEntity<TaskDTO> setAdvertisement(
            @AuthenticationPrincipal CustomUserDetail customUserDetail,
            @PathVariable("id") Long id,
            @PathVariable("advertisementId") Long advertisementId);

    ResponseEntity<Void> deleteTask(
            @AuthenticationPrincipal CustomUserDetail customUserDetail,
            @PathVariable("id") Long id);
}
