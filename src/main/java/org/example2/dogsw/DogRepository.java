package org.example2.dogsw;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DogRepository extends JpaRepository<DogEntity, Long> {
    List<DogEntity> findByBreed(String breed);
}
