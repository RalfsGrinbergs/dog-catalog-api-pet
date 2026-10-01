package dogs_catalog.controller;

import dogs_catalog.dto.DogDTO;
import dogs_catalog.service.DogService;
import dogs_catalog.dto.PatchingDogDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<DogDTO>> findAll(
            @RequestParam(required = false) String breed
    ) {
         if(breed != null) {
             return ResponseEntity.ok(dogService.findByBreed(breed));

         }
        return ResponseEntity.ok(dogService.findAll());
}
    @GetMapping("/{id}")
    public ResponseEntity<DogDTO> findById(@PathVariable("id") Long id) {

        return ResponseEntity.ok(dogService.findById(id));



    }
@PostMapping
    public ResponseEntity<DogDTO> addDog(@Valid @RequestBody DogDTO dogToAdd) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(dogService.addDog(dogToAdd));
}

@DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDog(@PathVariable("id") Long id) {
            dogService.deleteDog(id);
            return ResponseEntity.noContent().build();


}
@PatchMapping("/{id}")
    public ResponseEntity<DogDTO> updateDog(@PathVariable("id") Long id,
                                                  @Valid  @RequestBody PatchingDogDTO updatingDog) {
            return ResponseEntity.ok(dogService.updateDog(id, updatingDog));


}
}
