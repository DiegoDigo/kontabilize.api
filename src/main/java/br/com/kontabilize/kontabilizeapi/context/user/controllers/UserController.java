package br.com.kontabilize.kontabilizeapi.context.user.controllers;

import br.com.kontabilize.kontabilizeapi.context.user.command.input.SignInCommand;
import br.com.kontabilize.kontabilizeapi.context.user.services.UserService;
import br.com.kontabilize.kontabilizeapi.shared.utility.JwtTokenUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {


    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenProvider;

    private final UserService userService;

    public UserController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/signin")
    public ResponseEntity signin(@RequestBody SignInCommand command) {
        try {
            String email = command.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, command.getPassword()));
            String token = jwtTokenProvider.createToken(email, this.userService.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User with email" + email + "not found")).getRole());
            Map<Object, Object> model = new HashMap<>();
            model.put("email", email);
            model.put("token", token);
            return ResponseEntity.ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

}
