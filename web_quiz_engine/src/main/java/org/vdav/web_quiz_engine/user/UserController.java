package org.vdav.web_quiz_engine.user;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserDetailsServiceImpl userDetailsService;

    public UserController(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/register")
    @ResponseStatus(code = HttpStatus.OK)
    public void registerUser(@Valid @RequestBody RegistrationRequest registrationRequest) {
        userDetailsService.register(registrationRequest);
    }

}
