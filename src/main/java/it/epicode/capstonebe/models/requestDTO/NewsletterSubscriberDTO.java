package it.epicode.capstonebe.models.requestDTO;

import jakarta.validation.constraints.NotBlank;

public record NewsletterSubscriberDTO(

        @NotBlank(message = "Email is required and cannot be blank")
        String email

) {
}
