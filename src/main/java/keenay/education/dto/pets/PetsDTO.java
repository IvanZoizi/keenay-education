package keenay.education.dto.pets;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class PetsDTO {
    private Long id;
    private String nameAnimal;
    private String namePet;
    private String breed;
    private String features;
    private String vaccinations;
}
