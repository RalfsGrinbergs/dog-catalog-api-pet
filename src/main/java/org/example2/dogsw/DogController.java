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
    public ResponseEntity<List<DogDTO>> findAllDogs(
            @RequestParam(required = false) String breed
    ) {
         if(breed != null) {
             return ResponseEntity.status(200)
                     .body(dogService.findByBreed(breed));
         }
        return ResponseEntity.ok(dogService.findAll());
}
@PostMapping("/add")
    public ResponseEntity<DogDTO> addDog(@RequestBody DogDTO dogToAdd) {
        return ResponseEntity.status(201)
                .body(dogService.addDog(dogToAdd));
}
@GetMapping("/{id}")
    public ResponseEntity<DogDTO> findById(@PathVariable("id") Long id) {

        return ResponseEntity.status(200)
                .body(dogService.findById(id));


}
@DeleteMapping("/{id}")
    public ResponseEntity<DogDTO> deleteDog(@PathVariable("id") Long id) {
            return ResponseEntity.status(200)
                    .body(dogService.deleteDog(id));

}
@PatchMapping("/{id}")
    public ResponseEntity<DogDTO> updateDog(@PathVariable("id") Long id,
                                                    @RequestBody PatchingDogDTO updatingDog) {
            return ResponseEntity.status(200)
                    .body(dogService.updateDog(id, updatingDog));


}
}
