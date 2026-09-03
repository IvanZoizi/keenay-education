package keenay.education.repository;

import keenay.education.entity.Pets;
import keenay.education.entity.PetsProfile;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetsProfileRepository extends JpaRepository<PetsProfile, Long> {
}
