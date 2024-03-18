package it.epicode.capstonebe.models.requestDTO;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UserUpdateDTO(
        @Nullable
        String name,
        @Nullable
        String surname,
        @Nullable
        String username,
        @Nullable
        String phoneNumber,
        @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email not valid")
        String email
) {
}
