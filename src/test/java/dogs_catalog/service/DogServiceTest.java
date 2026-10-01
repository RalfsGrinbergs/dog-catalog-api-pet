package dogs_catalog.service;

import dogs_catalog.dto.DogDTO;
import dogs_catalog.entity.DogEntity;
import dogs_catalog.entity.OwnerEntity;
import dogs_catalog.exception.OwnerDogLimitExceededException;
import dogs_catalog.repository.DogRepository;
import dogs_catalog.repository.OwnerRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DogServiceTest {

    @Test
    void addDog() {
        DogRepository dogRepository = mock(DogRepository.class);
        OwnerRepository ownerRepository = mock(OwnerRepository.class);
        DogService dogService = new DogService(dogRepository, ownerRepository);

        DogDTO dogToAdd = new DogDTO(
                null,
                "Buddy",
                "Labrador",
                3,
                20.5,
                1L
        );
        OwnerEntity ownerEntity = new OwnerEntity("Alex Morgan");
        ownerEntity.setId(1L);
        when(ownerRepository.findById(1L)).thenReturn(Optional.of(ownerEntity));
        when(dogRepository.countByOwnerEntity_Id(1L)).thenReturn(5L);
       assertThrows(
               OwnerDogLimitExceededException.class,
               () -> dogService.addDog(dogToAdd)
       );

    }
    @Test
    void addDogSucscess() {
        DogRepository dogRepository = mock(DogRepository.class);
        OwnerRepository ownerRepository = mock(OwnerRepository.class);
        DogService dogService = new DogService(dogRepository, ownerRepository);
        DogDTO dogToAdd = new DogDTO(
                null,
                "Buddy",
                "Labrador",
                3,
                20.5,
                1L
        );
        OwnerEntity ownerEntity = new OwnerEntity("Alex Morgan");
        ownerEntity.setId(1L);
        when(ownerRepository.findById(1L)).thenReturn(Optional.of(ownerEntity));
        when(dogRepository.countByOwnerEntity_Id(1L)).thenReturn(4L);
        when(dogRepository.save(any(DogEntity.class)))
                    .thenAnswer(invocation -> invocation.getArgument(0));
        DogDTO dog = dogService.addDog(dogToAdd);
        assertEquals("Buddy", dog.name());
        assertEquals(1L, dog.ownerId());

    }
}
