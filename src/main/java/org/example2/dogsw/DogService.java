package org.example2.dogsw;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DogService {
private final DogRepository repository;

    public DogService(DogRepository repository) {
        this.repository = repository;
    }
    public List<Dogs> findAll() {
        List<DogEntity> allEntities = repository.findAll();
        return allEntities.stream()
                .map(it -> toDomainDogs(it)).toList();
    }
    private Dogs toDomainDogs(DogEntity dog) {
        return new Dogs(
                dog.getId(),
                dog.getName(),
                dog.getBreed(),
                dog.getAge(),
                dog.getWeight()

        );
    }
}
