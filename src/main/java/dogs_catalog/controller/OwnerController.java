package dogs_catalog.controller;

import dogs_catalog.dto.DogDTO;
import dogs_catalog.dto.OwnerDTO;
import dogs_catalog.service.OwnerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owners")
public class OwnerController {
    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }
    @GetMapping
    public ResponseEntity<List<OwnerDTO>> findAll() {
        return ResponseEntity.ok(ownerService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<OwnerDTO> findById(@PathVariable("id") Long id) {

    return ResponseEntity.ok(ownerService.findById(id));
    }
    @GetMapping("/{id}/dogs")
    public ResponseEntity<List<DogDTO>> findOwnerDogs(@PathVariable("id") Long id) {
        return ResponseEntity.ok(ownerService.findOwnerDogs(id));

    }
    @PostMapping
    public ResponseEntity<OwnerDTO> addOwner(@Valid @RequestBody OwnerDTO ownerToAdd) {
        return ResponseEntity.status(201)
                .body(ownerService.addOwner(ownerToAdd));

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<OwnerDTO> deleteOwner(@PathVariable("id") Long id) {
        ownerService.deleteOwner(id);
        return ResponseEntity.noContent().build();

    }

}
