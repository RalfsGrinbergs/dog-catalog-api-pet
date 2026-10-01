package dogs_catalog.repository;

public interface OwnerDogCountProjection {
    Long getOwnerId();
    Long getDogsCount();
}