package fitnesstracker.applications;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/applications")
public class ApplicationRestController {

    private final ApplicationService applicationService;

    public ApplicationRestController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerApplication(@Valid @RequestBody ApplicationRegistrationRequest request,
                                                 Authentication authentication) {
        String authenticatedUserEmail = authentication.getName();
        try {
            ApplicationRegistrationResponse response =
                    applicationService.registerApplication(request, authenticatedUserEmail);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalAccessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}