package keenay.education.controllers.http.impl;

import jakarta.validation.Valid;
import keenay.education.controllers.http.AdvertisementController;
import keenay.education.dto.advertisement.AdvertisementBodyDTO;
import keenay.education.dto.advertisement.AdvertisementBodyStatusDTO;
import keenay.education.dto.advertisement.AdvertisementDTO;
import keenay.education.dto.support.TicketBodyDTO;
import keenay.education.security.CustomUserDetail;
import keenay.education.service.AdvertisementService;
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
public class AdvertisementControllerImpl implements AdvertisementController {
    private final AdvertisementService advertisementService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_customer')")
    public ResponseEntity<AdvertisementDTO> createAdvertisement(
            @AuthenticationPrincipal CustomUserDetail userDetail,
            @Valid @RequestBody AdvertisementBodyDTO advertisementBodyDTO
    ) {
        return ResponseEntity.ok(advertisementService.createAdvertisement(userDetail, advertisementBodyDTO));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_customer')")
    public ResponseEntity<AdvertisementDTO> getAdvertisement(
            @AuthenticationPrincipal CustomUserDetail userDetail,
            @PathVariable("id") Long id
    ) {
        return ResponseEntity.ok(advertisementService.getAdvertisement(userDetail, id));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_customer')")
    public ResponseEntity<List<AdvertisementDTO>> getAdvertisements(
            @AuthenticationPrincipal CustomUserDetail userDetail
    ) {
        return ResponseEntity.ok(advertisementService.getAdvertisements(userDetail));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_customer')")
    public ResponseEntity<Void> deleteAdvertisement(
            @AuthenticationPrincipal CustomUserDetail userDetail,
            @PathVariable("id") Long id
    ) {
        advertisementService.deleteAdvertisement(userDetail, id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/task/{taskId}")
    @PreAuthorize("hasAuthority('ROLE_customer')")
    public ResponseEntity<AdvertisementDTO> addTask(
            @AuthenticationPrincipal CustomUserDetail userDetail,
            @PathVariable("id") Long id,
            @PathVariable("taskId") Long taskId
    ) {
        return ResponseEntity.ok(advertisementService.addTask(userDetail, id, taskId));
    }

    @DeleteMapping("/{id}/task/{taskId}")
    @PreAuthorize("hasAuthority('ROLE_customer')")
    public ResponseEntity<AdvertisementDTO> deleteTask(
            @AuthenticationPrincipal CustomUserDetail userDetail,
            @PathVariable("id") Long id,
            @PathVariable("taskId") Long taskId
    ) {
        return ResponseEntity.ok(advertisementService.deleteTask(userDetail, id, taskId));
    }

    // TODO проверить код
    @PutMapping("/status/{id}")
    @PreAuthorize("hasAuthority('ROLE_customer')")
    public ResponseEntity<AdvertisementDTO> updateStatus(
            @AuthenticationPrincipal CustomUserDetail userDetail,
            @PathVariable("id") Long id,
            @Valid @RequestBody AdvertisementBodyStatusDTO advertisementBodyStatusDTO
    ) {
        return ResponseEntity.ok(advertisementService.updateStatus(userDetail, id, advertisementBodyStatusDTO));
    }

    @PutMapping("/{id}/response/{responseId}")
    @PreAuthorize("hasAuthority('ROLE_customer')")
    public ResponseEntity<AdvertisementDTO> setResponse(
            @AuthenticationPrincipal CustomUserDetail userDetail,
            @PathVariable("id") Long id,
            @PathVariable("responseId") Long responseId
    ) {
        return ResponseEntity.ok(advertisementService.setResponse(userDetail, id, responseId));
    }

    @GetMapping("/by/skill")
    @PreAuthorize("hasAuthority('ROLE_seller')")
    public ResponseEntity<List<AdvertisementDTO>> getAdvertisementBySkills(
            @AuthenticationPrincipal CustomUserDetail userDetail
    ) {
        return ResponseEntity.ok(advertisementService.getAdvertisementBySkills(userDetail));
    }
}
