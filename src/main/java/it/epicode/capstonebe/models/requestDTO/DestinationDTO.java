package it.epicode.capstonebe.models.requestDTO;

import jakarta.validation.constraints.NotBlank;

public record DestinationDTO (
        @NotBlank(message = "The field \"name\" cannot be empty/null")
        String name,
        @NotBlank(message = "The field \"description\" cannot be empty/null")
        String description
) {
}