package keenay.education.service;

import keenay.education.dto.advertisement_response.AdvertisementResponseBodyDTO;
import keenay.education.dto.advertisement_response.AdvertisementResponseBodyStatusDTO;
import keenay.education.dto.advertisement_response.AdvertisementResponseDTO;
import keenay.education.entity.AdvertisementResponse;
import keenay.education.security.CustomUserDetail;

import java.util.List;

public interface AdvertisementResponseService {
    AdvertisementResponseDTO createAdvertisementResponse(CustomUserDetail customUserDetail,
                                                         AdvertisementResponseBodyDTO advertisementResponseBodyDTO);
    AdvertisementResponseDTO getAdvertisementResponse(CustomUserDetail customUserDetail, Long id);
    List<AdvertisementResponseDTO> getAdvertisementResponses(CustomUserDetail customUserDetail);
    List<AdvertisementResponseDTO> getResponses(CustomUserDetail customUserDetail, Long id);
    AdvertisementResponseDTO updateStatus(CustomUserDetail customUserDetail, Long id,
                                          AdvertisementResponseBodyStatusDTO advertisementResponseBodyStatusDTO);
    void deleteAdvertisementResponse(CustomUserDetail customUserDetail, Long id);
}
