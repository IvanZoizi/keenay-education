package keenay.education.mapper.skills;

import keenay.education.dto.skills.SkillsDTO;
import keenay.education.entity.Skills;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface SkillMapper {
    @Mappings({
            @Mapping(source = "skill.id", target="id"),
            @Mapping(source = "skill.title", target = "title"),
            @Mapping(source = "skill.description", target = "description"),
            @Mapping(source = "skill.animal.name", target = "animal")
    })
    SkillsDTO getSkill(Skills skill);
}
