package fitnesstracker.config;

import fitnesstracker.applications.ApplicationCategoryType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

public class AccessTokenFilter extends OncePerRequestFilter {

    private final RequestMatcher matcher = new OrRequestMatcher(
            new AntPathRequestMatcher("/api/tracker", HttpMethod.POST.name()),
            new AntPathRequestMatcher("/api/tracker", HttpMethod.GET.name())
    );

    private final AuthenticationEntryPoint authenticationEntryPoint = ((request, response, authException) ->  {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(authException.getMessage());
    });

    private final AuthenticationManager manager;
    private final RateLimiterService rateLimiterService;

    public AccessTokenFilter(AuthenticationManager manager, RateLimiterService rateLimiterService) {
        this.manager = manager;
        this.rateLimiterService = rateLimiterService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (matcher.matches(request)) {
            try {
                String token = request.getHeader("X-API-Key");
                if (!Objects.isNull(token)) {
                    Authentication authentication = new AccessTokenAuthentication(token);
                    authentication = manager.authenticate(authentication);

                    // Apply rate limiting for BASIC applications only
                    AccessTokenAuthentication auth = (AccessTokenAuthentication) authentication;
                    if (ApplicationCategoryType.BASIC.equals(auth.getApplicationCategory()) &&
                            !rateLimiterService.tryConsume(token)) {
                        response.setStatus(429);
                        response.getWriter().write("Rate limit exceeded");
                        return;
                    }

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    filterChain.doFilter(request, response);
                    return;
                }
                throw new BadCredentialsException("Access token is required");
            } catch (AuthenticationException e) {
                authenticationEntryPoint.commence(request, response, e);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
