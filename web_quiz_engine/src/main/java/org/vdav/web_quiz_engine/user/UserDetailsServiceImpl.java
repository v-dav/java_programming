package org.vdav.web_quiz_engine.user;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserDetailsServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = repository.findUserEntityByUsername(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("Not found");
        }
        return new UserAdapter(user);
    }

    public void register(RegistrationRequest request) {
        try {
            repository.save(
                    new UserEntity(
                            request.email(),
                            passwordEncoder.encode(request.password())
                    )
            );
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Email is already taken");
        }
    }
}
