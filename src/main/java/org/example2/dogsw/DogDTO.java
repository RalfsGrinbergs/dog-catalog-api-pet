package org.example2.dogsw;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record DogDTO(Long id,
                     @NotBlank
                     String name,
                     @NotBlank
                     String breed,
                     @Min(0)
                     int age,
                     @Positive
                     double weight) {

}
