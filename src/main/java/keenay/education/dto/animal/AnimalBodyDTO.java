package keenay.education.dto.animal;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class AnimalBodyDTO {
    @NonNull
    private String name;
}
