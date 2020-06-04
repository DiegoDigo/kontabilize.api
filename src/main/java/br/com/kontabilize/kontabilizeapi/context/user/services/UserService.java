package br.com.kontabilize.kontabilizeapi.context.user.services;

import br.com.kontabilize.kontabilizeapi.context.user.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {

    Optional<User> findByEmail(String email);
    User save(User user);

}
