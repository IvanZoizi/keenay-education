package keenay.education.controllers.http.impl;

import jakarta.validation.Valid;
import keenay.education.controllers.http.TaskController;
import keenay.education.dto.tasks.TaskBodyDTO;
import keenay.education.dto.tasks.TaskBodyStatusDTO;
import keenay.education.dto.tasks.TaskDTO;
import keenay.education.security.CustomUserDetail;
import keenay.education.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class TasksControllerImpl implements TaskController {

    private final TaskService taskService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ROLE_customer')")
    public ResponseEntity<TaskDTO> createTask(
            @AuthenticationPrincipal CustomUserDetail customUserDetail,
            @Valid @ModelAttribute TaskBodyDTO taskBodyDTO,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        return ResponseEntity.ok(taskService.createTask(customUserDetail, taskBodyDTO, file));
    }

    @Override
    @GetMapping("/")
    @PreAuthorize("hasAuthority('ROLE_customer')")
    public ResponseEntity<TaskDTO> getTask(
            @AuthenticationPrincipal CustomUserDetail customUserDetail,
            @PathVariable("id")  Long id
    ) {
        return ResponseEntity.ok(taskService.getTask(customUserDetail, id));
    }

    @Override
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_customer')")
    public ResponseEntity<List<TaskDTO>> getAvailTasks(@AuthenticationPrincipal CustomUserDetail customUserDetail) {
        return ResponseEntity.ok(taskService.getAvailTasks(customUserDetail));
    }

    @Override
    @GetMapping("/created")
    @PreAuthorize("hasAuthority('ROLE_customer')")
    public ResponseEntity<List<TaskDTO>> getCreatedTasks(@AuthenticationPrincipal CustomUserDetail customUserDetail) {
        return ResponseEntity.ok(taskService.getCreatedTasks(customUserDetail));
    }

    @Override
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_customer')")
    public ResponseEntity<TaskDTO> updateTask(
            @AuthenticationPrincipal CustomUserDetail customUserDetail,
            @PathVariable("id") Long id,
            @Valid @RequestBody TaskBodyDTO taskBodyDTO
    ) {
        return ResponseEntity.ok(taskService.updateTask(customUserDetail, id, taskBodyDTO));
    }

    @Override
    @PutMapping(value = "/photo/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ROLE_customer')")
    public ResponseEntity<TaskDTO> updatePhotoTask(
            @AuthenticationPrincipal CustomUserDetail customUserDetail,
            @PathVariable("id") Long id,
            @RequestParam("file") MultipartFile photo
    ) {
        return ResponseEntity.ok(taskService.updatePhotoTask(customUserDetail, id, photo));
    }

    @Override
    @PutMapping("/status/{id}")
    @PreAuthorize("hasAuthority('ROLE_customer')")
    public ResponseEntity<TaskDTO> updateTaskStatus(
            @AuthenticationPrincipal CustomUserDetail customUserDetail,
            @PathVariable("id") Long id,
            @Valid @RequestBody TaskBodyStatusDTO taskBodyStatusDTO
    ) {
        return ResponseEntity.ok(taskService.updateTaskStatus(customUserDetail, id, taskBodyStatusDTO));
    }

    @Override
    @PostMapping("/{id}/advertisement/{advertisementId}")
    @PreAuthorize("hasAuthority('ROLE_customer')")
    public ResponseEntity<TaskDTO> setAdvertisement(
            @AuthenticationPrincipal CustomUserDetail customUserDetail,
            @PathVariable("id") Long id,
            @PathVariable("advertisementId") Long advertisementId
    ) {
        return ResponseEntity.ok(taskService.setAdvertisement(customUserDetail, id, advertisementId));
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_customer')")
    public ResponseEntity<Void> deleteTask(
            @AuthenticationPrincipal CustomUserDetail customUserDetail,
            @PathVariable("id") Long id
    ) {
        taskService.deleteTask(customUserDetail, id);
        return ResponseEntity.noContent().build();
    }
}
