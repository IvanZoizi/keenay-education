package keenay.education.dto.skills;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class SkillsDTO {
    private Long id;
    private String title;
    private String description;
    private String animal;
}
