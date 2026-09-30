package dogs_catalog.repository;

import dogs_catalog.entity.OwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<OwnerEntity, Long> {
}
