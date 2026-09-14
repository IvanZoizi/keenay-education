package keenay.education.controllers.http;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import keenay.education.dto.advertisement.AdvertisementBodyDTO;
import keenay.education.dto.advertisement.AdvertisementDTO;
import keenay.education.security.CustomUserDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Tag(name = "Advertisement Endpoints")
@RequestMapping("/api/v1/advertisement")
public interface AdvertisementController {
    ResponseEntity<AdvertisementDTO> createAdvertisement(
            @AuthenticationPrincipal CustomUserDetail userDetail,
            @Valid @RequestBody AdvertisementBodyDTO advertisementBodyDTO
    );

    ResponseEntity<List<AdvertisementDTO>> getAdvertisements(
            @AuthenticationPrincipal CustomUserDetail userDetail
    );

    ResponseEntity<AdvertisementDTO> getAdvertisement(
            @AuthenticationPrincipal CustomUserDetail userDetail,
            @PathVariable("id") Long id
    );

    ResponseEntity<Void> deleteAdvertisement(
            @AuthenticationPrincipal CustomUserDetail userDetail,
            @PathVariable("id") Long id
    );

    ResponseEntity<AdvertisementDTO> addTask(
            @AuthenticationPrincipal CustomUserDetail userDetail,
            @PathVariable("id") Long id,
            @PathVariable("taskId") Long taskId
    );
    ResponseEntity<AdvertisementDTO> deleteTask(
            @AuthenticationPrincipal CustomUserDetail userDetail,
            @PathVariable("id") Long id,
            @PathVariable("taskId") Long taskId
    );
}
