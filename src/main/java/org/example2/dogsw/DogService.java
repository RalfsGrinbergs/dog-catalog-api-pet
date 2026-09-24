package org.example2.dogsw;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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
    public Dogs FindById(Long id) {
        DogEntity dogToFind =  repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("There is not dog with id: " +id));
return toDomainDogs(dogToFind);
    }
    @Transactional
    public Dogs addDog(Dogs dogToAdd) {
        var dogToSave = new DogEntity(
                dogToAdd.name(),
                dogToAdd.breed(),
                dogToAdd.age(),
                dogToAdd.weight()
        );

        var savedDog = repository.save(dogToSave);
        return toDomainDogs(savedDog);
    }
    public Dogs deleteDog(Long id) {
        DogEntity dogForDelete = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("There is not dog founded by id: " + id));
        repository.delete(dogForDelete);
        return toDomainDogs(dogForDelete);
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
