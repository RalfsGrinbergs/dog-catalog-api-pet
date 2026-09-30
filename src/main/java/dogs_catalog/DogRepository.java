package dogs_catalog;

import dogs_catalog.entity.DogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DogRepository extends JpaRepository<DogEntity, Long> {
    List<DogEntity> findByBreed(String breed);
}
