package keenay.education.service;

import keenay.education.dto.pets.PetsBodyDTO;
import keenay.education.dto.pets.PetsDTO;
import keenay.education.dto.pets.PetsPutBodyDTO;
import keenay.education.security.CustomUserDetail;

import java.util.List;

public interface PetsService {
    PetsDTO createPets(CustomUserDetail userDetail, PetsBodyDTO petsBodyDTO);
    List<PetsDTO> getListPets(CustomUserDetail userDetail);
    PetsDTO getPet(CustomUserDetail userDetail, Long id);
    PetsDTO updatePet(CustomUserDetail userDetail, Long id, PetsPutBodyDTO petsBodyDTO);
    void deletePet(CustomUserDetail userDetail, Long id);
}
