package keenay.education.controller;

import keenay.education.controllers.http.impl.SkillsControllerImpl;
import keenay.education.dto.skills.SkillsBodyDTO;
import keenay.education.dto.skills.SkillsDTO;
import keenay.education.dto.skills.SkillsPutBodyDTO;
import keenay.education.security.CustomUserDetail;
import keenay.education.service.SkillsService;
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
public class SkillsControllerImplTest {

    private static final Long SKILL_ID = 10L;

    @Mock
    private SkillsService skillsService;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private CustomUserDetail userDetail;

    @InjectMocks
    private SkillsControllerImpl skillsController;

    private SkillsBodyDTO body() {
        SkillsBodyDTO body = new SkillsBodyDTO();
        body.setTitle("Title");
        body.setDescription("Description");
        body.setAnimal("Dog");
        return body;
    }

    private SkillsPutBodyDTO putBody() {
        SkillsPutBodyDTO body = new SkillsPutBodyDTO();
        body.setTitle("Title");
        body.setDescription("Description");
        return body;
    }

    private SkillsDTO dto(Long id) {
        SkillsDTO dto = new SkillsDTO();
        dto.setId(id);
        dto.setTitle("Title");
        dto.setDescription("Description");
        dto.setAnimal("Dog");
        return dto;
    }

    @Test
    @DisplayName("Тест createSkillForUser")
    public void testCreateSkillForUserSuccess() {
        SkillsDTO expected = dto(SKILL_ID);
        when(skillsService.createSkillForUser(eq(userDetail), any(SkillsBodyDTO.class))).thenReturn(expected);

        ResponseEntity<SkillsDTO> response = skillsController.createSkillForUser(userDetail, body());

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
        verify(skillsService).createSkillForUser(eq(userDetail), any(SkillsBodyDTO.class));
    }

    @Test
    @DisplayName("Тест getSkills")
    public void testGetSkillsSuccess() {
        List<SkillsDTO> expected = List.of(dto(1L), dto(2L));
        when(skillsService.getSkills(userDetail)).thenReturn(expected);

        ResponseEntity<List<SkillsDTO>> response = skillsController.getSkills(userDetail);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(1L, response.getBody().get(0).getId());
        assertEquals(2L, response.getBody().get(1).getId());
        verify(skillsService).getSkills(userDetail);
    }

    @Test
    @DisplayName("Тест getSkills пустой список")
    public void testGetSkillsEmpty() {
        when(skillsService.getSkills(userDetail)).thenReturn(List.of());

        ResponseEntity<List<SkillsDTO>> response = skillsController.getSkills(userDetail);

        assertNotNull(response);
        assertTrue(response.getBody().isEmpty());
        verify(skillsService).getSkills(userDetail);
    }

    @Test
    @DisplayName("Тест getSkill")
    public void testGetSkillSuccess() {
        SkillsDTO expected = dto(SKILL_ID);
        when(skillsService.getSkill(userDetail, SKILL_ID)).thenReturn(expected);

        ResponseEntity<SkillsDTO> response = skillsController.getSkill(userDetail, SKILL_ID);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
        verify(skillsService).getSkill(userDetail, SKILL_ID);
    }

    @Test
    @DisplayName("Тест updateSkill")
    public void testUpdateSkillSuccess() {
        SkillsDTO expected = dto(SKILL_ID);
        when(skillsService.updateSkill(eq(userDetail), eq(SKILL_ID), any(SkillsPutBodyDTO.class)))
                .thenReturn(expected);

        ResponseEntity<SkillsDTO> response = skillsController.updateSkill(userDetail, SKILL_ID, putBody());

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
        verify(skillsService).updateSkill(eq(userDetail), eq(SKILL_ID), any(SkillsPutBodyDTO.class));
    }

    @Test
    @DisplayName("Тест deleteSkill")
    public void testDeleteSkillSuccess() {
        doNothing().when(skillsService).deleteSkill(userDetail, SKILL_ID);

        ResponseEntity<Void> response = skillsController.deleteSkill(userDetail, SKILL_ID);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(skillsService).deleteSkill(userDetail, SKILL_ID);
    }
}