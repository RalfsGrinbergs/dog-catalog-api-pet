package dogs_catalog.service;


import dogs_catalog.dto.OwnerDTO;
import dogs_catalog.entity.OwnerEntity;
import dogs_catalog.repository.DogRepository;
import dogs_catalog.repository.OwnerRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class OwnerServiceTest {
    @Test
    void findByIdReturnsOwner() {
        DogRepository dogRepository = mock(DogRepository.class);
        OwnerRepository ownerRepository = mock(OwnerRepository.class);
        OwnerService ownerService = new OwnerService(ownerRepository, dogRepository);
        OwnerEntity owner = new OwnerEntity("Alex Morgan");
        owner.setId(1L);
        when(ownerRepository.findById(1L)).thenReturn(Optional.of(owner));
        OwnerDTO ownerEntity = ownerService.findById(1L);
        assertEquals(1L, ownerEntity.id());
        assertEquals("Alex Morgan", ownerEntity.name());

    }
}
