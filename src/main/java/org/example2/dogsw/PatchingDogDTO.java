package org.example2.dogsw;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

public record PatchingDogDTO(

        String name,

        String breed,
        @Min(0)
        Integer age,
        @Positive
        Double weight
) {
}
