package org.example2.dogsw;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dogs")
public class DogController {
    private final DogService dogService;

    public DogController(DogService dogService) {
        this.dogService = dogService;
    }

    @GetMapping
    public ResponseEntity<List<DogDTO>> findAllDogs() {
        return ResponseEntity.ok(dogService.findAll());
}
@PostMapping("/add")
    public ResponseEntity<DogDTO> addDog(@RequestBody DogDTO dogToAdd) {
        return ResponseEntity.status(201)
                .body(dogService.addDog(dogToAdd));
}
@GetMapping("/{id}")
    public ResponseEntity<DogDTO> findById(@PathVariable("id") Long id) {
try{
        return ResponseEntity.status(200)
                .body(dogService.FindById(id));
} catch (EntityNotFoundException noId) {
    return ResponseEntity.status(404).build();
}
}
@DeleteMapping("/{id}")
    public ResponseEntity<DogDTO> deleteDog(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.status(200)
                    .body(dogService.deleteDog(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).build();
        }
}
@PatchMapping("/{id}")
    public ResponseEntity<DogDTO> updateDog(@PathVariable("id") Long id,
                                                    @RequestBody PatchingDogDTO updatingDog) {
        try {
            return ResponseEntity.status(200)
                    .body(dogService.updateDog(id, updatingDog));
        } catch(EntityNotFoundException e) {
            return ResponseEntity.status(404).build();
        }
}
}
