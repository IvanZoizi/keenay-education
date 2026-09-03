package keenay.education.service;

import keenay.education.dto.skills.SkillsBodyDTO;
import keenay.education.dto.skills.SkillsDTO;
import keenay.education.dto.skills.SkillsPutBodyDTO;
import keenay.education.entity.Skills;
import keenay.education.security.CustomUserDetail;

import java.util.List;

public interface SkillsService {
    SkillsDTO createSkillForUser(CustomUserDetail userDetail, SkillsBodyDTO skillsBodyDTO);
    List<SkillsDTO> getSkills(CustomUserDetail userDetail);
    SkillsDTO getSkill(CustomUserDetail userDetail, Long id);
    SkillsDTO updateSkill(CustomUserDetail userDetail, Long id, SkillsPutBodyDTO skillsPutBodyDTO);
    void deleteSkill(CustomUserDetail userDetail, Long id);
}
