package keenay.education.controllers.http.impl;

import jakarta.validation.Valid;
import keenay.education.controllers.http.SkillsController;
import keenay.education.dto.skills.SkillsBodyDTO;
import keenay.education.dto.skills.SkillsDTO;
import keenay.education.dto.skills.SkillsPutBodyDTO;
import keenay.education.security.CustomUserDetail;
import keenay.education.service.SkillsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class SkillsControllerImpl implements SkillsController {

    private final SkillsService skillsService;

    @Override
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_seller')")
    public ResponseEntity<SkillsDTO> createSkillForUser(
            @AuthenticationPrincipal CustomUserDetail userDetail,
            @Valid @RequestBody SkillsBodyDTO skillsBodyDTO) {
        return ResponseEntity.ok(skillsService.createSkillForUser(userDetail, skillsBodyDTO));
    }

    @Override
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_seller')")
    public ResponseEntity<List<SkillsDTO>> getSkills(@AuthenticationPrincipal CustomUserDetail userDetail) {
        return ResponseEntity.ok(skillsService.getSkills(userDetail));
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_seller')")
    public ResponseEntity<SkillsDTO> getSkill(
            @AuthenticationPrincipal CustomUserDetail userDetail,
            @PathVariable("id") Long id
    ) {
        return ResponseEntity.ok(skillsService.getSkill(userDetail, id));
    }

    @Override
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_seller')")
    public ResponseEntity<SkillsDTO> updateSkill(
            @AuthenticationPrincipal CustomUserDetail userDetail,
            @PathVariable("id") Long id,
            @Valid @RequestBody SkillsPutBodyDTO skillsPutBodyDTO
    ) {
        return ResponseEntity.ok(skillsService.updateSkill(userDetail, id, skillsPutBodyDTO));
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_seller')")
    public ResponseEntity<Void> deleteSkill(
            @AuthenticationPrincipal CustomUserDetail userDetail,
            @PathVariable("id") Long id
    ) {
        skillsService.deleteSkill(userDetail, id);
        return ResponseEntity.noContent().build();
    }
}
