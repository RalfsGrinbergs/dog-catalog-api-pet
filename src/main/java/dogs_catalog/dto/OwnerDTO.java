package dogs_catalog.dto;

import jakarta.validation.constraints.NotBlank;

public record OwnerDTO(

        Long id,
        @NotBlank
        String name,

        long dogsCount
)
{

}

