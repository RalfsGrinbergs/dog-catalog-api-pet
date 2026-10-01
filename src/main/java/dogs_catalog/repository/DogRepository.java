package dogs_catalog.repository;

import dogs_catalog.entity.DogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface DogRepository extends JpaRepository<DogEntity, Long> {
    List<DogEntity> findByBreed(String breed);
    long countByOwnerEntity_Id(Long ownerId);
    @Query("""
    select dog.ownerEntity.id as ownerId, count(dog) as dogsCount
    from DogEntity dog
    where dog.ownerEntity.id in :ownerIds
    group by dog.ownerEntity.id
    """)
    List<OwnerDogCountProjection> countDogsByOwnerIds(
            @Param("ownerIds") Collection<Long> ownerIds
    );

}
