package dogs_catalog.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Owners")
public class OwnerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "ownerEntity", fetch = FetchType.LAZY)
    private List<DogEntity> dogEntity;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public OwnerEntity() {
    }

    public OwnerEntity(String name) {
        this.name = name;
    }

    public List<DogEntity> getDogEntity() {
        return dogEntity;
    }

    public void setDogEntity(List<DogEntity> dogEntity) {
        this.dogEntity = dogEntity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
