package it.epicode.capstonebe.models.requestDTO;

import it.epicode.capstonebe.models.entities.Destination;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record TripDTO (
        @NotBlank(message = "The field \"departureDate\" cannot be empty/null")
        LocalDate departureDate,
        @NotBlank(message = "The field \"returningDate\" cannot be empty/null")
        LocalDate returningDate,
        @NotBlank(message = "The field \"price\" cannot be empty/null")
        Double price,
        @NotBlank(message = "The field \"maxPartecipants\" cannot be empty/null")
        Integer maxPartecipants,
        @NotBlank(message = "The field \"returningDate\" cannot be empty/null")
        @Min(value = 1, message = "The minimum value for the destination id is 1")
        Long destinationId
        ) {
}