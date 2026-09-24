package keenay.education.controller;

import keenay.education.controllers.http.impl.PetsControllerImpl;
import keenay.education.dto.pets.PetsBodyDTO;
import keenay.education.dto.pets.PetsDTO;
import keenay.education.dto.pets.PetsPutBodyDTO;
import keenay.education.security.CustomUserDetail;
import keenay.education.service.PetsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PetsControllerImplTest {

    private static final Long PET_ID = 10L;

    @Mock
    private PetsService petsService;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private CustomUserDetail userDetail;

    @InjectMocks
    private PetsControllerImpl petsController;

    private PetsBodyDTO body() {
        PetsBodyDTO body = new PetsBodyDTO();
        body.setNameAnimal("Dog");
        body.setNamePet("Buddy");
        body.setBreed("Labrador");
        body.setFeatures("Friendly");
        body.setVaccinations("Yes");
        return body;
    }

    private PetsPutBodyDTO putBody() {
        PetsPutBodyDTO body = new PetsPutBodyDTO();
        body.setBreed("Labrador");
        body.setFeatures("Friendly");
        body.setVaccinations("Yes");
        return body;
    }

    private PetsDTO dto(Long id) {
        PetsDTO dto = new PetsDTO();
        dto.setId(id);
        dto.setNameAnimal("Dog");
        dto.setNamePet("Buddy");
        dto.setBreed("Labrador");
        dto.setFeatures("Friendly");
        dto.setVaccinations("Yes");
        return dto;
    }

    @Test
    @DisplayName("Тест createPets")
    public void testCreatePetsSuccess() {
        PetsDTO expected = dto(PET_ID);
        when(petsService.createPets(eq(userDetail), any(PetsBodyDTO.class))).thenReturn(expected);

        ResponseEntity<PetsDTO> response = petsController.createPets(userDetail, body());

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
        verify(petsService).createPets(eq(userDetail), any(PetsBodyDTO.class));
    }

    @Test
    @DisplayName("Тест getListPets")
    public void testGetListPetsSuccess() {
        List<PetsDTO> expected = List.of(dto(1L), dto(2L));
        when(petsService.getListPets(userDetail)).thenReturn(expected);

        ResponseEntity<List<PetsDTO>> response = petsController.getListPets(userDetail);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(1L, response.getBody().get(0).getId());
        assertEquals(2L, response.getBody().get(1).getId());
        verify(petsService).getListPets(userDetail);
    }

    @Test
    @DisplayName("Тест getListPets пустой список")
    public void testGetListPetsEmpty() {
        when(petsService.getListPets(userDetail)).thenReturn(List.of());

        ResponseEntity<List<PetsDTO>> response = petsController.getListPets(userDetail);

        assertNotNull(response);
        assertTrue(response.getBody().isEmpty());
        verify(petsService).getListPets(userDetail);
    }

    @Test
    @DisplayName("Тест getPet")
    public void testGetPetSuccess() {
        PetsDTO expected = dto(PET_ID);
        when(petsService.getPet(userDetail, PET_ID)).thenReturn(expected);

        ResponseEntity<PetsDTO> response = petsController.getPet(userDetail, PET_ID);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
        verify(petsService).getPet(userDetail, PET_ID);
    }

    @Test
    @DisplayName("Тест updatePet")
    public void testUpdatePetSuccess() {
        PetsDTO expected = dto(PET_ID);
        when(petsService.updatePet(eq(userDetail), eq(PET_ID), any(PetsPutBodyDTO.class)))
                .thenReturn(expected);

        ResponseEntity<PetsDTO> response = petsController.updatePet(userDetail, PET_ID, putBody());

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
        verify(petsService).updatePet(eq(userDetail), eq(PET_ID), any(PetsPutBodyDTO.class));
    }

    @Test
    @DisplayName("Тест deletePet")
    public void testDeletePetSuccess() {
        doNothing().when(petsService).deletePet(userDetail, PET_ID);

        ResponseEntity<Void> response = petsController.deletePet(userDetail, PET_ID);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(petsService).deletePet(userDetail, PET_ID);
    }
}