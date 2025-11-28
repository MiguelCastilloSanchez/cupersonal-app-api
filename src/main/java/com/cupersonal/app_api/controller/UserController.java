package com.cupersonal.app_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cupersonal.app_api.dto.request.AuthenticationDTO;
import com.cupersonal.app_api.dto.request.RegisterDTO;
import com.cupersonal.app_api.entity.User;
import com.cupersonal.app_api.repository.UserRepository;
import com.cupersonal.app_api.service.TokenService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins ="*")
@RequestMapping(value = "/auth")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;

    /**
     * Registers a new user.
     *
     * @param data Object containing user registration data
     * @param result Object checking the validation from registration data
     * @return ResponseEntity indicating success or failure of registration
     */
    @SuppressWarnings("rawtypes")
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity register(@Valid @RequestBody RegisterDTO data, BindingResult result) {

        if (result.hasErrors() || !(data.password().equals(data.confirmPassword()))) {
            String error = result.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(error);
        }

        if (
            (this.userRepository.findByEmail(data.email()) != null) ||
            (this.userRepository.findByName(data.name()) != null)
        ) return ResponseEntity.badRequest().body("Username or email already used");

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User user = User.builder()
            .name(data.name())
            .email(data.email())
            .password(encryptedPassword)
            .build();

        this.userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    /**
     * Authenticates user login.
     *
     * @param data Object containing user credentials
     * @return ResponseEntity containing authentication token
     */
    @SuppressWarnings("rawtypes")
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity login(@RequestBody AuthenticationDTO data, HttpServletResponse response) {
        var credentials = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        
        try {
            var auth = this.authenticationManager.authenticate(credentials);
            var token = tokenService.generateToken((User) auth.getPrincipal());

            ResponseCookie cookie = ResponseCookie.from("token", token)
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .sameSite("Strict")
                    .maxAge(60 * 60)
                    .build();

            response.addHeader("Set-Cookie", cookie.toString());

            return ResponseEntity.ok("Login successful");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password");
        }
    }

}
