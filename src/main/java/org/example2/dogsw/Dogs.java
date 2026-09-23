package org.example2.dogsw;

import jakarta.persistence.Column;

public record Dogs( Long id,
                    String name,
                    String breed,
                    int age,
                    double weight) {

}
