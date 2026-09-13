package keenay.education.controllers.http.impl;

import jakarta.validation.Valid;
import keenay.education.controllers.http.AdminController;
import keenay.education.dto.animal.AnimalBodyDTO;
import keenay.education.dto.animal.AnimalDTO;
import keenay.education.service.AnimalService;
import keenay.education.service.image.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AdminControllerImpl implements AdminController {

    private final AnimalService animalService;
    private final ImageService imageService;

    @Override
    @PostMapping("/animal")
    @PreAuthorize("hasAnyRole('ROLE_admin')")
    public ResponseEntity<AnimalDTO> createAnimal(@Valid @RequestBody AnimalBodyDTO animalBodyDTO) {
        return ResponseEntity.ok(animalService.createAnimal(animalBodyDTO));
    }

    @Override
    @GetMapping("/animal")
    public ResponseEntity<List<AnimalDTO>> getAnimals() {
        return ResponseEntity.ok(animalService.getAnimals());
    }

    @Override
    @DeleteMapping("/animal")
    @PreAuthorize("hasAnyRole('ROLE_admin')")
    public ResponseEntity<Void> deleteAnimal(@Valid @RequestBody AnimalBodyDTO animalBodyDTO) {
        animalService.deleteAnimal(animalBodyDTO);
        return ResponseEntity.noContent().build();
    }
//
//    @PostMapping(value = "/test/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<String> testPhoto(@RequestParam("file") MultipartFile file)  {
//        return ResponseEntity.ok(imageService.uploadPhoto(file));
//    }
}
