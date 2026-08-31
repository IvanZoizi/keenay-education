package keenay.education.repository;

import keenay.education.entity.PetsProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetsProfileRepository extends JpaRepository<PetsProfile, Long> {
}
