package dogs_catalog.service;

import dogs_catalog.dto.DogDTO;
import dogs_catalog.dto.OwnerDTO;
import dogs_catalog.entity.DogEntity;
import dogs_catalog.entity.OwnerEntity;
import dogs_catalog.repository.DogRepository;
import dogs_catalog.repository.OwnerDogCountProjection;
import dogs_catalog.repository.OwnerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        if (allEntities.isEmpty()) {
            return List.of();
        }

        Map<Long, Long> dogCounts = dogRepository.countDogsByOwnerIds(
                allEntities.stream()
                        .map(OwnerEntity::getId)
                        .toList()
        ).stream().collect(Collectors.toMap(
                OwnerDogCountProjection::getOwnerId,
                OwnerDogCountProjection::getDogsCount
        ));

        return allEntities.stream()
                .map(owner -> toDomainOwner(
                        owner,
                        dogCounts.getOrDefault(owner.getId(), 0L)
                ))
                .toList();
    }

    public OwnerDTO findById(Long id) {
        OwnerEntity ownerToFind = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "There is not owner with that id " + id
                ));
        return toDomainOwner(ownerToFind);
    }

    @Transactional
    public OwnerDTO addOwner(OwnerDTO ownerToAdd) {
        var ownerToSave = new OwnerEntity(ownerToAdd.name());
        var savedOwner = repository.save(ownerToSave);
        return toDomainOwner(savedOwner);
    }

    @Transactional
    public void deleteOwner(Long id) {
        OwnerEntity ownerToDelete = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "There is not owner with that id " + id
                ));
        repository.delete(ownerToDelete);
    }

    @Transactional // keeps the lazy dogs accessible
    public List<DogDTO> findOwnerDogs(Long id) {
        OwnerEntity ownerToFindDogs = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "There is not owner with that id " + id
                ));

        return ownerToFindDogs.getDogEntity().stream()
                .map(this::toDogDTO)
                .toList();
    }

    private OwnerDTO toDomainOwner(OwnerEntity owner) {
        long dogsCount = dogRepository.countByOwnerEntity_Id(owner.getId());
        return toDomainOwner(owner, dogsCount);
    }

    private OwnerDTO toDomainOwner(OwnerEntity owner, long dogsCount) {
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
                dog.getWeight(),
                dog.getOwnerEntity() == null ? null : dog.getOwnerEntity().getId()
        );
    }
}