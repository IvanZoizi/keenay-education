package keenay.education.repository;

import keenay.education.entity.Pets;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetsRepository extends JpaRepository<Pets, Long> {
    @EntityGraph(attributePaths = {"petsProfile", "animal"})
    List<Pets> findByCustomer_Id(Long id);

    @EntityGraph(attributePaths = {"petsProfile", "animal"})
    List<Pets> findAll();

    @EntityGraph(attributePaths = {"petsProfile", "animal"})
    Optional<Pets> findById(Long id);
}
