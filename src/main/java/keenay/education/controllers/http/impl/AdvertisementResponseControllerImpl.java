package keenay.education.controllers.http.impl;

import jakarta.validation.Valid;
import keenay.education.controllers.http.AdvertisementResponseController;
import keenay.education.dto.advertisement.AdvertisementBodyDTO;
import keenay.education.dto.advertisement.AdvertisementDTO;
import keenay.education.dto.advertisement_response.AdvertisementResponseBodyDTO;
import keenay.education.dto.advertisement_response.AdvertisementResponseBodyStatusDTO;
import keenay.education.dto.advertisement_response.AdvertisementResponseDTO;
import keenay.education.security.CustomUserDetail;
import keenay.education.service.AdvertisementResponseService;
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
public class AdvertisementResponseControllerImpl implements AdvertisementResponseController {
    private final AdvertisementResponseService advertisementResponseService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_seller')")
    public ResponseEntity<AdvertisementResponseDTO> createAdvertisement(
            @AuthenticationPrincipal CustomUserDetail userDetail,
            @Valid @RequestBody AdvertisementResponseBodyDTO advertisementBodyDTO
    ) {
        return ResponseEntity.ok(advertisementResponseService.createAdvertisementResponse(userDetail, advertisementBodyDTO));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_seller')")
    public ResponseEntity<AdvertisementResponseDTO> getAdvertisement(
            @AuthenticationPrincipal CustomUserDetail userDetail,
            @PathVariable("id") Long id
    ) {
        return ResponseEntity.ok(advertisementResponseService.getAdvertisementResponse(userDetail, id));
    }

    @GetMapping("/created/{id}")
    @PreAuthorize("hasAuthority('ROLE_seller')")
    public ResponseEntity<List<AdvertisementResponseDTO>> getResponses(
            @AuthenticationPrincipal CustomUserDetail userDetail,
            @PathVariable("id") Long id
    ) {
        return ResponseEntity.ok(advertisementResponseService.getResponses(userDetail, id));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_seller')")
    public ResponseEntity<List<AdvertisementResponseDTO>> getAdvertisements(
            @AuthenticationPrincipal CustomUserDetail userDetail
    ) {
        return ResponseEntity.ok(advertisementResponseService.getAdvertisementResponses(userDetail));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_seller')")
    public ResponseEntity<Void> deleteAdvertisements(
            @AuthenticationPrincipal CustomUserDetail userDetail,
            @PathVariable("id") Long id
            ) {
        advertisementResponseService.deleteAdvertisementResponse(userDetail, id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/status/{id}")
    @PreAuthorize("hasAuthority('ROLE_seller')")
    public ResponseEntity<AdvertisementResponseDTO> updateStatus(
            @AuthenticationPrincipal CustomUserDetail userDetail,
            @PathVariable("id") Long id,
            @Valid @RequestBody AdvertisementResponseBodyStatusDTO advertisementResponseBodyStatusDTO
    ) {
        return ResponseEntity.ok(advertisementResponseService.updateStatus(userDetail, id, advertisementResponseBodyStatusDTO));
    }

}
