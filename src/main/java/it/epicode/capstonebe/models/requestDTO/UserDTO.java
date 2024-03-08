package it.epicode.capstonebe.models.requestDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDTO (

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
        @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email not valid")
        String email

){
}
