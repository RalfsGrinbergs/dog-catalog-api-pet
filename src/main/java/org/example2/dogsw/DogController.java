package org.example2.dogsw;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

@RestController
@RequestMapping("/dogs")
public class DogController {
    private final DogService dogService;

    public DogController(DogService dogService) {
        this.dogService = dogService;
    }

    @GetMapping
    public ResponseEntity<List<Dogs>> findAllDogs() {
        return ResponseEntity.ok(dogService.findAll());
}
@PostMapping("/add")
    public ResponseEntity<Dogs> addDog(@RequestBody Dogs dogToAdd) {
        return ResponseEntity.status(201)
                .body(dogService.addDog(dogToAdd));
}
@GetMapping("/{id}")
    public ResponseEntity<Dogs> findById(@PathVariable("id") Long id) {
try{
        return ResponseEntity.status(200)
                .body(dogService.FindById(id));
} catch (EntityNotFoundException noId) {
    return ResponseEntity.status(404).build();
}
}
@DeleteMapping("/{id}")
    public ResponseEntity<Dogs> deleteDog(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.status(200)
                    .body(dogService.deleteDog(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).build();
        }
}
}
