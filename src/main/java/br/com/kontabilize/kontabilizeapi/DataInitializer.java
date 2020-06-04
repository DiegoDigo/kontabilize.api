package br.com.kontabilize.kontabilizeapi;

import br.com.kontabilize.kontabilizeapi.context.user.entities.User;
import br.com.kontabilize.kontabilizeapi.context.user.entities.enums.Role;
import br.com.kontabilize.kontabilizeapi.context.user.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {


    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public DataInitializer(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {


        Stream.of(new User("admin@admin.com", bCryptPasswordEncoder.encode("12345678"), Role.ADMIN))
                .forEach(user -> {
                    if (!userService.findByEmail(user.getEmail()).isPresent()) {
                        log.info("Criando usuario admin " + user.getEmail());
                        userService.save(user);
                    }else {
                        log.info("Usuario ja criado " + user.getEmail());
                    }
                });


    }
}
