package it.epicode.capstonebe.models.requestDTO;

import it.epicode.capstonebe.models.enums.UserRoles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record UserAdminDTO (
        @NotBlank(message = "The field \"name\" cannot be empty/null")
        String name,
        @NotBlank(message = "The field \"surname\" cannot be empty/null")
        String surname,
        @NotBlank(message = "The field \"username\" cannot be empty/null")
        String username,
        @NotBlank(message = "The field \"password\" cannot be empty/null")
        String password,
        @NotBlank(message = "The field \"phone number\" cannot be empty/null")
        String phoneNumber,
        @NotNull(message = "You must provide a birthday date")
        LocalDate birthday,
        List<UserRoles> userRoles,
        @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email not valid")
        String email
) {
}
