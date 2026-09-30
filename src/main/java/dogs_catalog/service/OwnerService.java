package dogs_catalog.service;


import dogs_catalog.dto.DogDTO;
import dogs_catalog.dto.OwnerDTO;
import dogs_catalog.entity.DogEntity;
import dogs_catalog.entity.OwnerEntity;
import dogs_catalog.repository.DogRepository;
import dogs_catalog.repository.OwnerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerService {
    private final OwnerRepository repository;
    private final DogRepository dogRepository;

    public OwnerService(OwnerRepository repository, DogRepository dogRepository) {
        this.repository = repository;
        this.dogRepository = dogRepository;
    }
    public List<OwnerDTO> findAll() {
        List<OwnerEntity> allEntities = repository.findAll();
        return allEntities.stream()
                .map(it -> toDomainOwner(it)).toList();
    }
    public OwnerDTO findById(Long id) {
        OwnerEntity ownerToFind = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("There is not owner with that id" + id));
        return toDomainOwner(ownerToFind);
    }
    @Transactional
    public OwnerDTO addOwner(OwnerDTO ownerToAdd) {
        var ownerToSave = new OwnerEntity(
                ownerToAdd.name()

        );
        var savedOwner = repository.save(ownerToSave);
        return toDomainOwner(savedOwner);

    }
    @Transactional
    public OwnerDTO deleteOwner(Long id) {
        OwnerEntity ownerToDelete = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("There is not owner with that id" + id));
        repository.delete(ownerToDelete);
        return toDomainOwner(ownerToDelete);

    }
    @Transactional // for eager
    public List<DogDTO> findOwnerDogs(Long id) {
        OwnerEntity ownerToFindDogs = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("There is not owner with that id " + id));
       return ownerToFindDogs.getDogEntity().stream()
               .map(this::toDogDTO)
               .toList();


    }
    private OwnerDTO toDomainOwner(OwnerEntity owner) {
        long dogsCount = dogRepository.countByOwnerEntity_Id(owner.getId());
        return new OwnerDTO(
                owner.getId(),
                owner.getName(),
                dogsCount


        );
    }
    private DogDTO toDogDTO(DogEntity dog) {
        return new DogDTO(
                dog.getId(),
                dog.getName(),
                dog.getBreed(),
                dog.getAge(),
                dog.getWeight()
        );
    }
}
