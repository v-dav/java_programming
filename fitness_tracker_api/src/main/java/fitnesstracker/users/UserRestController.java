package fitnesstracker.users;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/developers")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest signupRequest) {
        try {
            UserDto user = userService.signup(signupRequest);
            return ResponseEntity.created(URI.create("/api/developers/" + user.id())).build();
        } catch (IllegalAccessException e) {
            return ResponseEntity.badRequest().body("UserEntity already exists");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDeveloperById(@PathVariable UUID id, Authentication authentication) {
        String authenticatedUserEmail = authentication.getName();
        try {
            return ResponseEntity.ok(userService.getDeveloperById(id, authenticatedUserEmail));
        } catch (AccessDeniedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
