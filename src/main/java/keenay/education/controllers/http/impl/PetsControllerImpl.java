package keenay.education.controllers.http.impl;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import keenay.education.controllers.http.PetsController;
import keenay.education.dto.pets.PetsBodyDTO;
import keenay.education.dto.pets.PetsDTO;
import keenay.education.dto.pets.PetsPutBodyDTO;
import keenay.education.security.CustomUserDetail;
import keenay.education.service.PetsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PetsControllerImpl implements PetsController {

    private final PetsService petsService;

    @Override
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_customer')")
//    @PreAuthorize("hasAnyRole('ROLE_customer')")
    public ResponseEntity<PetsDTO> createPets(
            @AuthenticationPrincipal CustomUserDetail userDetail,
            @Valid @RequestBody PetsBodyDTO petsBodyDTO) {
        return ResponseEntity.ok(petsService.createPets(userDetail, petsBodyDTO));
    }

    @Override
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_customer')")
    public ResponseEntity<List<PetsDTO>> getListPets(@AuthenticationPrincipal CustomUserDetail userDetail) {
        return ResponseEntity.ok(petsService.getListPets(userDetail));
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_customer')")
    public ResponseEntity<PetsDTO> getPet(
            @AuthenticationPrincipal CustomUserDetail userDetail,
            @PathVariable ("id") Long id) {
        return ResponseEntity.ok(petsService.getPet(userDetail, id));
    }

    @Override
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_customer')")
    public ResponseEntity<PetsDTO> updatePet(
            @AuthenticationPrincipal CustomUserDetail userDetail,
            @PathVariable ("id") Long id,
            @Valid @RequestBody PetsPutBodyDTO petsBodyDTO) {
        return ResponseEntity.ok(petsService.updatePet(userDetail, id, petsBodyDTO));
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_customer')")
    public ResponseEntity<Void> deletePet(
            @AuthenticationPrincipal CustomUserDetail userDetail,
            @PathVariable ("id") Long id) {
        petsService.deletePet(userDetail, id);
        return ResponseEntity.noContent().build();
    }
}
