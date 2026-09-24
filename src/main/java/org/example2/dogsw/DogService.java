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
    public List<DogDTO> findAll() {
        List<DogEntity> allEntities = repository.findAll();
        return allEntities.stream()
                .map(it -> toDomainDogs(it)).toList();
    }
    public DogDTO findById(Long id) {
        DogEntity dogToFind =  repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("There is not dog with id: " +id));
return toDomainDogs(dogToFind);
    }
    @Transactional
    public DogDTO addDog(DogDTO dogToAdd) {
        var dogToSave = new DogEntity(
                dogToAdd.name(),
                dogToAdd.breed(),
                dogToAdd.age(),
                dogToAdd.weight()
        );

        var savedDog = repository.save(dogToSave);
        return toDomainDogs(savedDog);
    }
    @Transactional
    public DogDTO deleteDog(Long id) {
        DogEntity dogForDelete = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("There is not dog founded by id: " + id));
        repository.delete(dogForDelete);
        return toDomainDogs(dogForDelete);
    }
    @Transactional
    public DogDTO updateDog(Long id, PatchingDogDTO dogToUpdate) {
        DogEntity dogForUpdate = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("There is not dog founded by id:" + id));
        if(dogToUpdate.name() != null) {
            dogForUpdate.setName(dogToUpdate.name());
        }
        if (dogToUpdate.breed() != null) {
            dogForUpdate.setBreed(dogToUpdate.breed());
        }

        if (dogToUpdate.age() != null) {
            dogForUpdate.setAge(dogToUpdate.age());
        }

        if (dogToUpdate.weight() != null) {
            dogForUpdate.setWeight(dogToUpdate.weight());
        }

        return toDomainDogs(dogForUpdate);

    }
    public List<DogDTO> findByBreed(String breed) {
        List<DogDTO> founded = repository.findByBreed(breed).stream()
                .map(it -> toDomainDogs(it))
                .toList();
        return founded;
    }

    private DogDTO toDomainDogs(DogEntity dog) {
        return new DogDTO(
                dog.getId(),
                dog.getName(),
                dog.getBreed(),
                dog.getAge(),
                dog.getWeight()

        );
    }




}
