package br.com.kontabilize.kontabilizeapi.infra.repositories.services;

import br.com.kontabilize.kontabilizeapi.context.user.entities.User;
import br.com.kontabilize.kontabilizeapi.context.user.services.UserService;
import br.com.kontabilize.kontabilizeapi.infra.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Collections.emptyList;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> applicationUser = this.userRepository.findByEmail(email);
        if (!applicationUser.isPresent()) {
            throw new UsernameNotFoundException(email);
        }
        return new org.springframework.security.core.userdetails.User(applicationUser.get().getUsername(), applicationUser.get().getPassword(), emptyList());
    }

    public Optional<User> findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
