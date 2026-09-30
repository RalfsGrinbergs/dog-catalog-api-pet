package dogs_catalog.service;

import dogs_catalog.dto.DogDTO;
import dogs_catalog.dto.OwnerDTO;
import dogs_catalog.entity.DogEntity;
import dogs_catalog.entity.OwnerEntity;
import dogs_catalog.repository.OwnerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerService {
    private final OwnerRepository repository;

    public OwnerService(OwnerRepository repository) {
        this.repository = repository;
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
        return toDomainOwner(ownerToSave);

    }

    private OwnerDTO toDomainOwner(OwnerEntity owner) {
        return new OwnerDTO(
                owner.getId(),
                owner.getName()


        );
    }
}
