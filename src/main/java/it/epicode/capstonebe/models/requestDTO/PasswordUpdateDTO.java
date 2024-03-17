package it.epicode.capstonebe.models.requestDTO;

import jakarta.validation.constraints.NotBlank;

public record PasswordUpdateDTO(
        @NotBlank(message = "The field \"oldPassword\" cannot be empty/null")
        String oldPassword,
        @NotBlank(message = "The field \"newPassword\" cannot be empty/null")
        String newPassword
) {
}
