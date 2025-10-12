package fitnesstracker.config;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class AccessTokenConfigurer extends AbstractHttpConfigurer<AccessTokenConfigurer, HttpSecurity> {

    private final RateLimiterService rateLimiterService;

    public AccessTokenConfigurer(RateLimiterService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        AuthenticationManager manager = builder.getSharedObject(AuthenticationManager.class);
        builder.addFilterAfter(
                new AccessTokenFilter(manager, rateLimiterService),
                UsernamePasswordAuthenticationFilter.class
        );
    }
}
