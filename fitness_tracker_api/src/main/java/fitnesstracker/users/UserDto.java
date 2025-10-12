package fitnesstracker.users;

import fitnesstracker.applications.ApplicationDto;

import java.util.List;
import java.util.UUID;

public record UserDto(UUID id, String email, List<ApplicationDto> applications) {
}