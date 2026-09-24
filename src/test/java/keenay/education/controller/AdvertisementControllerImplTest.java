package keenay.education.controller;

import keenay.education.controllers.http.impl.AdvertisementControllerImpl;
import keenay.education.dto.advertisement.AdvertisementBodyDTO;
import keenay.education.dto.advertisement.AdvertisementBodyStatusDTO;
import keenay.education.dto.advertisement.AdvertisementDTO;
import keenay.education.entity.status.AdvertisementStatus;
import keenay.education.exception.errors.AdvertisementNotFoundException;
import keenay.education.exception.errors.PetsNotFoundException;
import keenay.education.security.CustomUserDetail;
import keenay.education.service.AdvertisementService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdvertisementControllerImplTest {

    private static final Long AD_ID = 10L;

    @Mock
    private AdvertisementService advertisementService;

    @Mock
    private CustomUserDetail userDetail;

    @InjectMocks
    private AdvertisementControllerImpl advertisementController;

    private AdvertisementDTO dto(Long id) {
        AdvertisementDTO dto = new AdvertisementDTO();
        dto.setId(id);
        dto.setBudget(1500);
        return dto;
    }

    @Test
    @DisplayName("Тест createAdvertisement")
    public void testCreateAdvertisement() {
        AdvertisementBodyDTO body = new AdvertisementBodyDTO();
        body.setPetId(20L);
        body.setBudget(1500);
        body.setListTasksId(List.of(100L, 101L));
        AdvertisementDTO expectedDto = dto(AD_ID);

        when(advertisementService.createAdvertisement(userDetail, body)).thenReturn(expectedDto);

        ResponseEntity<AdvertisementDTO> response = advertisementController.createAdvertisement(userDetail, body);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDto, response.getBody());
        verify(advertisementService).createAdvertisement(userDetail, body);
        verifyNoMoreInteractions(advertisementService);
    }

    @Test
    @DisplayName("Тест createAdvertisement null")
    public void testCreateAdvertisementPetNotFound() {
        AdvertisementBodyDTO body = new AdvertisementBodyDTO();
        body.setPetId(9999L);
        body.setBudget(1500);
        body.setListTasksId(List.of());

        when(advertisementService.createAdvertisement(userDetail, body))
                .thenThrow(new PetsNotFoundException("This pet is not found."));

        assertThrows(PetsNotFoundException.class,
                () -> advertisementController.createAdvertisement(userDetail, body));
    }

    @Test
    @DisplayName("Тест getAdvertisement")
    public void testGetAdvertisement() {
        AdvertisementDTO expectedDto = dto(AD_ID);

        when(advertisementService.getAdvertisement(userDetail, AD_ID)).thenReturn(expectedDto);

        ResponseEntity<AdvertisementDTO> response = advertisementController.getAdvertisement(userDetail, AD_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(AD_ID, response.getBody().getId());
        assertEquals(1500, response.getBody().getBudget());
        verify(advertisementService).getAdvertisement(userDetail, AD_ID);
    }

    @Test
    @DisplayName("Тест getAdvertisement null")
    public void testGetAdvertisementNotFound() {
        when(advertisementService.getAdvertisement(userDetail, 9999L))
                .thenThrow(new AdvertisementNotFoundException("Advertisement is not found."));

        assertThrows(AdvertisementNotFoundException.class,
                () -> advertisementController.getAdvertisement(userDetail, 9999L));
        verify(advertisementService).getAdvertisement(userDetail, 9999L);
    }

    @Test
    @DisplayName("Тест getAdvertisements")
    public void testGetAdvertisements() {
        List<AdvertisementDTO> expected = List.of(dto(1L), dto(2L));

        when(advertisementService.getAdvertisements(userDetail)).thenReturn(expected);

        ResponseEntity<List<AdvertisementDTO>> response = advertisementController.getAdvertisements(userDetail);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(expected, response.getBody());
        verify(advertisementService).getAdvertisements(userDetail);
    }

    @Test
    @DisplayName("Тест getAdvertisements пустой список")
    public void testGetAdvertisementsEmpty() {
        when(advertisementService.getAdvertisements(userDetail)).thenReturn(List.of());

        ResponseEntity<List<AdvertisementDTO>> response = advertisementController.getAdvertisements(userDetail);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    @DisplayName("Тест getAdvertisementBySkills")
    public void testGetAdvertisementBySkills() {
        List<AdvertisementDTO> expected = List.of(dto(AD_ID));

        when(advertisementService.getAdvertisementBySkills(userDetail)).thenReturn(expected);

        ResponseEntity<List<AdvertisementDTO>> response = advertisementController.getAdvertisementBySkills(userDetail);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
        verify(advertisementService).getAdvertisementBySkills(userDetail);
    }

    @Test
    @DisplayName("Тест updateStatus")
    public void testUpdateStatus() {
        AdvertisementBodyStatusDTO body = new AdvertisementBodyStatusDTO(AdvertisementStatus.PROGRESS);
        AdvertisementDTO expectedDto = dto(AD_ID);
        expectedDto.setAdvertisementStatus(AdvertisementStatus.PROGRESS);

        when(advertisementService.updateStatus(userDetail, AD_ID, body)).thenReturn(expectedDto);

        ResponseEntity<AdvertisementDTO> response = advertisementController.updateStatus(userDetail, AD_ID, body);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(AdvertisementStatus.PROGRESS, response.getBody().getAdvertisementStatus());
        verify(advertisementService).updateStatus(userDetail, AD_ID, body);
    }

    @Test
    @DisplayName("Тест updateStatus null")
    public void testUpdateStatusNotFound() {
        AdvertisementBodyStatusDTO body = new AdvertisementBodyStatusDTO(AdvertisementStatus.PROGRESS);

        when(advertisementService.updateStatus(userDetail, 9999L, body))
                .thenThrow(new AdvertisementNotFoundException("Advertisement is not found."));

        assertThrows(AdvertisementNotFoundException.class,
                () -> advertisementController.updateStatus(userDetail, 9999L, body));
    }

    @Test
    @DisplayName("Тест addTask")
    public void testAddTask() {
        AdvertisementDTO expectedDto = dto(AD_ID);

        when(advertisementService.addTask(userDetail, AD_ID, 100L)).thenReturn(expectedDto);

        ResponseEntity<AdvertisementDTO> response = advertisementController.addTask(userDetail, AD_ID, 100L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDto, response.getBody());
        verify(advertisementService).addTask(userDetail, AD_ID, 100L);
    }

    @Test
    @DisplayName("Тест deleteTask")
    public void testDeleteTask() {
        AdvertisementDTO expectedDto = dto(AD_ID);

        when(advertisementService.deleteTask(userDetail, AD_ID, 100L)).thenReturn(expectedDto);

        ResponseEntity<AdvertisementDTO> response = advertisementController.deleteTask(userDetail, AD_ID, 100L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDto, response.getBody());
        verify(advertisementService).deleteTask(userDetail, AD_ID, 100L);
    }

    @Test
    @DisplayName("Тест setResponse")
    public void testSetResponse() {
        AdvertisementDTO expectedDto = dto(AD_ID);
        expectedDto.setSelectedResponse(50L);

        when(advertisementService.setResponse(userDetail, AD_ID, 50L)).thenReturn(expectedDto);

        ResponseEntity<AdvertisementDTO> response = advertisementController.setResponse(userDetail, AD_ID, 50L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(50L, response.getBody().getSelectedResponse());
        verify(advertisementService).setResponse(userDetail, AD_ID, 50L);
    }

    @Test
    @DisplayName("Тест deleteAdvertisement")
    public void testDeleteAdvertisement() {
        ResponseEntity<Void> response = advertisementController.deleteAdvertisement(userDetail, AD_ID);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(advertisementService).deleteAdvertisement(userDetail, AD_ID);
        verifyNoMoreInteractions(advertisementService);
    }

    @Test
    @DisplayName("Тест deleteAdvertisement null")
    public void testDeleteAdvertisementNotFound() {
        doThrow(new AdvertisementNotFoundException("Advertisement is not found."))
                .when(advertisementService).deleteAdvertisement(userDetail, 9999L);

        assertThrows(AdvertisementNotFoundException.class,
                () -> advertisementController.deleteAdvertisement(userDetail, 9999L));
    }
}