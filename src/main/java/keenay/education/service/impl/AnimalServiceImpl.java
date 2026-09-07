package keenay.education.service.impl;

import keenay.education.dto.animal.AnimalBodyDTO;
import keenay.education.dto.animal.AnimalDTO;
import keenay.education.entity.Animals;
import keenay.education.exception.errors.AnimalIsNotSupported;
import keenay.education.exception.errors.EntityNotFoundException;
import keenay.education.mapper.MapperService;
import keenay.education.repository.AnimalsRepository;
import keenay.education.service.AnimalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AnimalServiceImpl implements AnimalService {
    private final AnimalsRepository animalsRepository;
    private final MapperService mapper;

    @Override
    public AnimalDTO createAnimal(AnimalBodyDTO animalDTO) {
        Animals animal = new Animals();
        System.out.println(animalDTO);
        System.out.println(animalDTO.getName());
        animal.setName(animalDTO.getName());
        System.out.println(animal);
        return mapper.getAnimal(animalsRepository.save(animal));
    }

    @Override
    public List<AnimalDTO> getAnimals() {
        return animalsRepository.findAll().stream()
                .map(mapper::getAnimal)
                .toList();
    }

    @Override
    public void deleteAnimal(AnimalBodyDTO animalDTO) {
        Animals animal = animalsRepository.findByName(animalDTO.getName())
                .orElseThrow(() -> new AnimalIsNotSupported("No animal with the given name was found."));
        animalsRepository.delete(animal);
    }
}
