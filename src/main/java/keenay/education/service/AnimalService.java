package keenay.education.service;

import keenay.education.dto.animal.AnimalBodyDTO;
import keenay.education.dto.animal.AnimalDTO;

import java.util.List;

public interface AnimalService {
    AnimalDTO createAnimal(AnimalBodyDTO animalDTO);
    List<AnimalDTO> getAnimals();
    void deleteAnimal(AnimalBodyDTO animalDTO);
}
