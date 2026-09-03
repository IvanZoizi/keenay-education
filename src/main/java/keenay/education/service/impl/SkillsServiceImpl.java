package keenay.education.service.impl;

import keenay.education.dto.skills.SkillsBodyDTO;
import keenay.education.dto.skills.SkillsDTO;
import keenay.education.dto.skills.SkillsPutBodyDTO;
import keenay.education.entity.Animals;
import keenay.education.entity.Sellers;
import keenay.education.entity.Skills;
import keenay.education.exception.errors.AccessDeniedException;
import keenay.education.exception.errors.EntityNotFoundException;
import keenay.education.mapper.MapperService;
import keenay.education.repository.AnimalsRepository;
import keenay.education.repository.SkillsRepository;
import keenay.education.security.CustomUserDetail;
import keenay.education.service.SkillsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SkillsServiceImpl implements SkillsService {

    private final SkillsRepository skillsRepository;
    private final AnimalsRepository animalsRepository;
    private final MapperService mapperService;

    @Override
    public SkillsDTO createSkillForUser(CustomUserDetail userDetail, SkillsBodyDTO skillsBodyDTO) {
        Animals animal = animalsRepository.findByName(skillsBodyDTO.getAnimal())
                .orElseThrow(() -> new EntityNotFoundException("This animal is not handled in our service."));
        Skills skill = new Skills();
        skill.setTitle(skillsBodyDTO.getTitle());
        skill.setDescription(skillsBodyDTO.getDescription());
        skill.setAnimal(animal);
        skill.setSeller(userDetail.getUser().getSeller());
        return mapperService.getSkill(skillsRepository.save(skill));
    }

    @Override
    public List<SkillsDTO> getSkills(CustomUserDetail userDetail) {
        return skillsRepository.findAll().stream()
                .map(mapperService::getSkill)
                .toList();
    }

    @Override
    public SkillsDTO getSkill(CustomUserDetail userDetail, Long id) {
        Skills skill = skillsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("This skill has not been found."));
        if (!skill.getSeller().getUser().getId().equals(userDetail.getUser().getId())) {
            throw new AccessDeniedException("You cannot obtain information about this pet.");
        }
        return mapperService.getSkill(skill);
    }

    @Override
    public SkillsDTO updateSkill(CustomUserDetail userDetail, Long id, SkillsPutBodyDTO skillsPutBodyDTO) {
        Skills skill = skillsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("This skill has not been found."));
        if (!skill.getSeller().getUser().getId().equals(userDetail.getUser().getId())) {
            throw new AccessDeniedException("You cannot obtain information about this pet.");
        }
        skill.setTitle(skillsPutBodyDTO.getTitle());
        skill.setDescription(skillsPutBodyDTO.getDescription());
        skill.setSeller(userDetail.getUser().getSeller());
        return mapperService.getSkill(skillsRepository.save(skill));
    }

    @Override
    public void deleteSkill(CustomUserDetail userDetail, Long id) {
        Skills skill = skillsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("This skill has not been found."));
        if (!skill.getSeller().getUser().getId().equals(userDetail.getUser().getId())) {
            throw new AccessDeniedException("You cannot obtain information about this pet.");
        }
        skillsRepository.delete(skill);
    }
}
