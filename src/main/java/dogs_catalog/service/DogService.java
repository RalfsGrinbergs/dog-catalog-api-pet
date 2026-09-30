package dogs_catalog.service;

import dogs_catalog.entity.OwnerEntity;
import dogs_catalog.repository.DogRepository;
import dogs_catalog.dto.DogDTO;
import dogs_catalog.dto.PatchingDogDTO;
import dogs_catalog.entity.DogEntity;
import dogs_catalog.repository.OwnerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DogService {
private final DogRepository repository;
    private final OwnerRepository ownerRepository;

    public DogService(DogRepository repository, OwnerRepository ownerRepository) {
        this.repository = repository;
        this.ownerRepository = ownerRepository;
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
        OwnerEntity owner = ownerRepository.findById(dogToAdd.ownerId())
                .orElseThrow(() -> new EntityNotFoundException("There is not owner with that id" + dogToAdd.ownerId()));

        var dogToSave = new DogEntity(
                dogToAdd.name(),
                dogToAdd.breed(),
                dogToAdd.age(),
                dogToAdd.weight()
        );
        dogToSave.setOwnerEntity(owner);


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
                dog.getWeight(),
                dog.getOwnerEntity() == null ? null : dog.getOwnerEntity().getId()

        );
    }




}
