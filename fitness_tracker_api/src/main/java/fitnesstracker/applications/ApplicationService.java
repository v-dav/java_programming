package fitnesstracker.applications;

import fitnesstracker.users.UserEntity;
import fitnesstracker.users.UserRepository;
import fitnesstracker.common.Mapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final Mapper mapper;

    public ApplicationService(ApplicationRepository applicationRepository,
                              UserRepository userRepository,
                              Mapper mapper) {
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public ApplicationRegistrationResponse registerApplication(ApplicationRegistrationRequest request,
                                                               String authenticatedUserEmail) throws IllegalAccessException {
        if (applicationRepository.existsByName(request.name())) {
            throw new IllegalAccessException("ApplicationEntity name already exists");
        }

        UserEntity userEntity = userRepository.findByEmail(authenticatedUserEmail)
                .orElseThrow(() -> new UsernameNotFoundException("UserEntity not found"));

        String apiKey = generateApiKey();

        ApplicationEntity applicationEntity = new ApplicationEntity(
                request.name(),
                request.description(),
                apiKey,
                userEntity,
                request.category()
        );

        ApplicationEntity savedApplicationEntity = applicationRepository.save(applicationEntity);

        return new ApplicationRegistrationResponse(
                savedApplicationEntity.getName(),
                savedApplicationEntity.getApikey(),
                savedApplicationEntity.getCategory()
        );
    }

    public List<ApplicationDto> getApplicationsByUser(UserEntity userEntity) {
        return applicationRepository
                .findByUserOrderByCreatedOnDesc(userEntity)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    private String generateApiKey() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}