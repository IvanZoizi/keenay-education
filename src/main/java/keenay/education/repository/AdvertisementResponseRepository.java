package keenay.education.repository;

import keenay.education.entity.AdvertisementResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertisementResponseRepository extends JpaRepository<AdvertisementResponse, Long> {
}
