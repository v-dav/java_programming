package fitnesstracker.applications;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ApplicationRegistrationRequest(
        @NotBlank String name,
        @NotNull String description,
        @NotNull ApplicationCategoryType category
) {}