package fitnesstracker.users;

import fitnesstracker.applications.ApplicationService;
import fitnesstracker.applications.ApplicationDto;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationService applicationService;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       ApplicationService applicationService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.applicationService = applicationService;
    }

    public UserDto signup(SignupRequest signupRequest) throws IllegalAccessException {
        if (userRepository.existsUserByEmail(signupRequest.email())) {
            throw new IllegalAccessException("UserEntity already exists");
        }
        UserEntity savedUserEntity = userRepository.save(
                new UserEntity(signupRequest.email(),
                        passwordEncoder.encode(signupRequest.password())
                )
        );

        return new UserDto(savedUserEntity.getId(), savedUserEntity.getEmail(), List.of());
    }

    public UserDto getDeveloperById(UUID id, String authenticatedUserEmail) {
        UserEntity authenticatedUserEntity = userRepository.findByEmail(authenticatedUserEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Authenticated userEntity not found"));

        if (!authenticatedUserEntity.getId().equals(id)) {
            throw new AccessDeniedException("Access denied: You can only access your own data");
        }

        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("UserEntity not found"));

        List<ApplicationDto> applications = applicationService.getApplicationsByUser(userEntity);
        return new UserDto(userEntity.getId(), userEntity.getEmail(), applications);
    }

}
