package keenay.education.controllers.http;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import keenay.education.dto.skills.SkillsBodyDTO;
import keenay.education.dto.skills.SkillsDTO;
import keenay.education.dto.skills.SkillsPutBodyDTO;
import keenay.education.security.CustomUserDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Tag(name = "Skills Endpoints")
@RequestMapping("/api/v1/skills")
public interface SkillsController {
    ResponseEntity<SkillsDTO> createSkillForUser(
            @AuthenticationPrincipal CustomUserDetail userDetail,
            @Valid @RequestBody SkillsBodyDTO skillsBodyDTO
    );
    ResponseEntity<List<SkillsDTO>> getSkills(
            @AuthenticationPrincipal CustomUserDetail userDetail
    );
    ResponseEntity<SkillsDTO> getSkill(
            @AuthenticationPrincipal CustomUserDetail userDetail,
            @PathVariable("id") Long id
    );
    ResponseEntity<SkillsDTO> updateSkill(
            @AuthenticationPrincipal CustomUserDetail userDetail,
            @PathVariable("id") Long id,
            @Valid @RequestBody SkillsPutBodyDTO skillsPutBodyDTO
    );
    ResponseEntity<Void> deleteSkill(
            @AuthenticationPrincipal CustomUserDetail userDetail,
            @PathVariable("id") Long id
    );
}
