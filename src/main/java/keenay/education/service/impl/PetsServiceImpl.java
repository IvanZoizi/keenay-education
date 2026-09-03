package keenay.education.service.impl;

import keenay.education.dto.pets.PetsBodyDTO;
import keenay.education.dto.pets.PetsDTO;
import keenay.education.dto.pets.PetsPutBodyDTO;
import keenay.education.entity.Animals;
import keenay.education.entity.Customers;
import keenay.education.entity.Pets;
import keenay.education.entity.PetsProfile;
import keenay.education.exception.errors.AccessDeniedException;
import keenay.education.exception.errors.EntityNotFoundException;
import keenay.education.mapper.MapperService;
import keenay.education.repository.AnimalsRepository;
import keenay.education.repository.PetsProfileRepository;
import keenay.education.repository.PetsRepository;
import keenay.education.repository.UserRepository;
import keenay.education.security.CustomUserDetail;
import keenay.education.service.PetsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PetsServiceImpl implements PetsService {

    private final PetsRepository petsRepository;
    private final PetsProfileRepository petsProfileRepository;
    private final AnimalsRepository animalsRepository;
    private final MapperService mapperService;

    private Pets createPets(Pets pets, PetsBodyDTO petsBodyDTO, Customers customer, Animals animal) {
        pets.setAnimal(animal);
        pets.setCustomer(customer);
        pets.setName(petsBodyDTO.getNamePet());
        return petsRepository.save(pets);
    }

    private PetsProfile createPetsProfile(PetsProfile petsProfile, PetsBodyDTO petsBodyDTO, Pets pet) {
        petsProfile.setPet(pet);
        petsProfile.setBreed(petsBodyDTO.getBreed());
        petsProfile.setFeatures(petsBodyDTO.getFeatures());
        petsProfile.setVaccinations(petsBodyDTO.getVaccinations());
        return petsProfileRepository.save(petsProfile);
    }

    @Override
    @Transactional
    public PetsDTO createPets(CustomUserDetail userDetail, PetsBodyDTO petsBodyDTO) {
        Customers customers = userDetail.getUser().getCustomer();
        Animals animal = animalsRepository.findByName(petsBodyDTO.getNameAnimal())
                .orElseThrow(() -> new EntityNotFoundException("This animal is not handled in our service."));
        Pets pets = new Pets();
        pets = createPets(pets, petsBodyDTO, customers, animal);
        pets.setPetsProfile(createPetsProfile(new PetsProfile(), petsBodyDTO, pets));
        return mapperService.getPets(pets);
    }

    @Override
    public List<PetsDTO> getListPets(CustomUserDetail userDetail) {
        return petsRepository.findByCustomer_Id(userDetail.getUser().getId()).stream()
                .map(mapperService::getPets)
                .toList();
    }

    @Override
    public PetsDTO getPet(CustomUserDetail userDetail, Long id) {
        Pets pet = petsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("The pet was not found."));
        if (!pet.getCustomer().getUser().getId().equals(userDetail.getUser().getId())) {
            throw new AccessDeniedException("You cannot obtain information about this pet.");
        }
        return mapperService.getPets(pet);
    }

    @Override
    public PetsDTO updatePet(CustomUserDetail userDetail, Long id, PetsPutBodyDTO petsBodyDTO) {
        Pets pet = petsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("The pet was not found."));
        if (!pet.getCustomer().getUser().getId().equals(userDetail.getUser().getId())) {
            throw new AccessDeniedException("You cannot obtain information about this pet.");
        }
        PetsProfile petsProfile = pet.getPetsProfile();
        petsProfile.setVaccinations(petsBodyDTO.getVaccinations());
        petsProfile.setFeatures(petsBodyDTO.getFeatures());
        petsProfile.setBreed(petsBodyDTO.getBreed());
        petsProfileRepository.save(petsProfile);
        pet.setPetsProfile(petsProfile);
        return mapperService.getPets(pet);
    }

    @Override
    public void deletePet(CustomUserDetail userDetail, Long id) {
        Pets pet = petsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("The pet was not found."));
        if (!pet.getCustomer().getUser().getId().equals(userDetail.getUser().getId())) {
            throw new AccessDeniedException("You cannot obtain information about this pet.");
        }
        petsRepository.delete(pet);
    }
}
