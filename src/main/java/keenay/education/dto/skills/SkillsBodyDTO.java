package keenay.education.dto.skills;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class SkillsBodyDTO {
    @NonNull
    private String title;
    @NonNull
    private String description;
    @NonNull
    private String animal;
}
