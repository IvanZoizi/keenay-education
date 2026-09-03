package keenay.education.controllers.http;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import keenay.education.dto.pets.PetsBodyDTO;
import keenay.education.dto.pets.PetsDTO;
import keenay.education.security.CustomUserDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Tag(name = "Pets Endpoints")
@RequestMapping("/api/v1/pets")
public interface PetsController {
    ResponseEntity<PetsDTO> createPets(
            @AuthenticationPrincipal CustomUserDetail userDetail,
            @Valid @RequestBody PetsBodyDTO petsBodyDTO
    );
    ResponseEntity<List<PetsDTO>> getListPets(
            @AuthenticationPrincipal CustomUserDetail userDetail
    );
    ResponseEntity<PetsDTO> getPet(
            @AuthenticationPrincipal CustomUserDetail userDetail,
            @PathVariable("id") Long id
    );
    ResponseEntity<PetsDTO> updatePet(
            @AuthenticationPrincipal CustomUserDetail userDetail,
            @PathVariable ("id") Long id,
            @Valid @RequestBody PetsBodyDTO petsBodyDTO
    );
    ResponseEntity<Void> deletePet(
            @AuthenticationPrincipal CustomUserDetail userDetail,
            @PathVariable ("id") Long id
    );
}
