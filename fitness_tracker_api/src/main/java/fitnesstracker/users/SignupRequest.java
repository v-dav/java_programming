package fitnesstracker.users;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignupRequest(@Valid @NotBlank @Email String email,
                            @Valid @NotBlank String password) {
}
