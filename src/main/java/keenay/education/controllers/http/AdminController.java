package keenay.education.controllers.http;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import keenay.education.dto.animal.AnimalBodyDTO;
import keenay.education.dto.animal.AnimalDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Tag(name = "Admin Endpoints")
@RequestMapping("/api/v1/admin")
public interface AdminController {
    ResponseEntity<AnimalDTO> createAnimal(@Valid AnimalBodyDTO animalBodyDTO);
    ResponseEntity<List<AnimalDTO>> getAnimals();
    ResponseEntity<Void> deleteAnimal(@Valid AnimalBodyDTO animalBodyDTO);
}
