package keenay.education.controller;

import keenay.education.controllers.http.impl.TasksControllerImpl;
import keenay.education.dto.tasks.TaskBodyDTO;
import keenay.education.dto.tasks.TaskBodyStatusDTO;
import keenay.education.dto.tasks.TaskDTO;
import keenay.education.entity.status.TasksStatus;
import keenay.education.security.CustomUserDetail;
import keenay.education.service.TaskService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TasksControllerImplTest {

    private static final Long TASK_ID = 10L;
    private static final Long AD_ID = 20L;

    @Mock
    private TaskService taskService;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private CustomUserDetail userDetail;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private TasksControllerImpl tasksController;

    private TaskDTO dto(Long id) {
        TaskDTO dto = new TaskDTO();
        dto.setId(id);
        dto.setTitle("Title");
        dto.setDescription("Description");
        dto.setStatus(TasksStatus.CREATED);
        return dto;
    }

    private TaskBodyDTO body() {
        TaskBodyDTO body = new TaskBodyDTO();
        body.setTitle("Title");
        body.setDescription("Description");
        return body;
    }

    private TaskBodyStatusDTO statusBody(TasksStatus status) {
        return new TaskBodyStatusDTO(status);
    }

    @Test
    @DisplayName("Тест createTask")
    public void testCreateTaskSuccess() {
        TaskDTO expected = dto(TASK_ID);
        when(taskService.createTask(eq(userDetail), any(TaskBodyDTO.class), eq(multipartFile)))
                .thenReturn(expected);

        ResponseEntity<TaskDTO> response = tasksController.createTask(userDetail, body(), multipartFile);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
        verify(taskService).createTask(eq(userDetail), any(TaskBodyDTO.class), eq(multipartFile));
    }

    @Test
    @DisplayName("Тест createTask без файла")
    public void testCreateTaskWithoutFile() {
        TaskDTO expected = dto(TASK_ID);
        when(taskService.createTask(eq(userDetail), any(TaskBodyDTO.class), isNull()))
                .thenReturn(expected);

        ResponseEntity<TaskDTO> response = tasksController.createTask(userDetail, body(), null);

        assertNotNull(response);
        assertEquals(expected, response.getBody());
        verify(taskService).createTask(eq(userDetail), any(TaskBodyDTO.class), isNull());
    }

    @Test
    @DisplayName("Тест getTask")
    public void testGetTaskSuccess() {
        TaskDTO expected = dto(TASK_ID);
        when(taskService.getTask(userDetail, TASK_ID)).thenReturn(expected);

        ResponseEntity<TaskDTO> response = tasksController.getTask(userDetail, TASK_ID);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
        verify(taskService).getTask(userDetail, TASK_ID);
    }

    @Test
    @DisplayName("Тест getAvailTasks")
    public void testGetAvailTasksSuccess() {
        List<TaskDTO> expected = List.of(dto(1L), dto(2L));
        when(taskService.getAvailTasks(userDetail)).thenReturn(expected);

        ResponseEntity<List<TaskDTO>> response = tasksController.getAvailTasks(userDetail);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(1L, response.getBody().get(0).getId());
        assertEquals(2L, response.getBody().get(1).getId());
        verify(taskService).getAvailTasks(userDetail);
    }

    @Test
    @DisplayName("Тест getAvailTasks пустой список")
    public void testGetAvailTasksEmpty() {
        when(taskService.getAvailTasks(userDetail)).thenReturn(List.of());

        ResponseEntity<List<TaskDTO>> response = tasksController.getAvailTasks(userDetail);

        assertNotNull(response);
        assertTrue(response.getBody().isEmpty());
        verify(taskService).getAvailTasks(userDetail);
    }

    @Test
    @DisplayName("Тест getCreatedTasks")
    public void testGetCreatedTasksSuccess() {
        List<TaskDTO> expected = List.of(dto(1L));
        when(taskService.getCreatedTasks(userDetail)).thenReturn(expected);

        ResponseEntity<List<TaskDTO>> response = tasksController.getCreatedTasks(userDetail);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(taskService).getCreatedTasks(userDetail);
    }

    @Test
    @DisplayName("Тест updateTask")
    public void testUpdateTaskSuccess() {
        TaskDTO expected = dto(TASK_ID);
        when(taskService.updateTask(eq(userDetail), eq(TASK_ID), any(TaskBodyDTO.class)))
                .thenReturn(expected);

        ResponseEntity<TaskDTO> response = tasksController.updateTask(userDetail, TASK_ID, body());

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
        verify(taskService).updateTask(eq(userDetail), eq(TASK_ID), any(TaskBodyDTO.class));
    }

    @Test
    @DisplayName("Тест updatePhotoTask")
    public void testUpdatePhotoTaskSuccess() {
        TaskDTO expected = dto(TASK_ID);
        when(taskService.updatePhotoTask(userDetail, TASK_ID, multipartFile)).thenReturn(expected);

        ResponseEntity<TaskDTO> response = tasksController.updatePhotoTask(userDetail, TASK_ID, multipartFile);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
        verify(taskService).updatePhotoTask(userDetail, TASK_ID, multipartFile);
    }

    @Test
    @DisplayName("Тест updateTaskStatus")
    public void testUpdateTaskStatusSuccess() {
        TaskDTO expected = dto(TASK_ID);
        expected.setStatus(TasksStatus.PROGRESS);
        when(taskService.updateTaskStatus(eq(userDetail), eq(TASK_ID), any(TaskBodyStatusDTO.class)))
                .thenReturn(expected);

        ResponseEntity<TaskDTO> response = tasksController.updateTaskStatus(
                userDetail, TASK_ID, statusBody(TasksStatus.PROGRESS));

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(TasksStatus.PROGRESS, response.getBody().getStatus());
        verify(taskService).updateTaskStatus(eq(userDetail), eq(TASK_ID), any(TaskBodyStatusDTO.class));
    }

    @Test
    @DisplayName("Тест setAdvertisement")
    public void testSetAdvertisementSuccess() {
        TaskDTO expected = dto(TASK_ID);
        when(taskService.setAdvertisement(userDetail, TASK_ID, AD_ID)).thenReturn(expected);

        ResponseEntity<TaskDTO> response = tasksController.setAdvertisement(userDetail, TASK_ID, AD_ID);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
        verify(taskService).setAdvertisement(userDetail, TASK_ID, AD_ID);
    }

    @Test
    @DisplayName("Тест deleteTask")
    public void testDeleteTaskSuccess() {
        doNothing().when(taskService).deleteTask(userDetail, TASK_ID);

        ResponseEntity<Void> response = tasksController.deleteTask(userDetail, TASK_ID);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(taskService).deleteTask(userDetail, TASK_ID);
    }
}