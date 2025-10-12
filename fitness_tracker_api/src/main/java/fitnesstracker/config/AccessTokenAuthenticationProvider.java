package fitnesstracker.config;

import fitnesstracker.applications.ApplicationEntity;
import fitnesstracker.applications.ApplicationRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class AccessTokenAuthenticationProvider implements AuthenticationProvider {

    private final ApplicationRepository repository;

    public AccessTokenAuthenticationProvider(ApplicationRepository repository) {
        this.repository = repository;
    }

    @Transactional(noRollbackFor = BadCredentialsException.class)
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Object credentials = authentication.getCredentials();
        if (credentials == null) {
            throw new BadCredentialsException("Access token is required");
        }

        String token = credentials.toString();
        Optional<ApplicationEntity> app = Optional.ofNullable(repository.findByApikey(token));

        if (app.isPresent()) {
            AccessTokenAuthentication auth = (AccessTokenAuthentication) authentication;
            auth.setAuthenticated(true);
            auth.setApplicationCategory(app.get().getCategory());
            return auth;
        } else {
            throw new BadCredentialsException("Invalid access token");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AccessTokenAuthentication.class.equals(authentication);
    }
}
