package it.epicode.capstonebe.models.requestDTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record BookingDTO (
    @NotBlank(message = "The field \"participantsNumber\" cannot be empty/null")
    int participantsNumber,
    @Min(value = 1, message = "The minimum value for the user id is 1")
    UUID userId,
    @NotBlank(message = "The field \"tripId\" cannot be empty/null")
    @Min(value = 1, message = "The minimum value for the trip id is 1")
    Long tripId


) {
}

