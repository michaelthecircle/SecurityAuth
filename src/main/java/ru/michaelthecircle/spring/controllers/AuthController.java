package ru.michaelthecircle.spring.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.michaelthecircle.spring.dtos.JwtRequest;
import ru.michaelthecircle.spring.dtos.JwtResponse;
import ru.michaelthecircle.spring.exceptions.AppError;
import ru.michaelthecircle.spring.services.UserService;
import ru.michaelthecircle.spring.utils.JwtTokenUtils;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthControllerAdapter {
    private final UserService userService; //нам придет юзер с паролем, нужно будет сформировать токен
    private final JwtTokenUtils jwtTokenUtils;
    //private final AuthenticationManager authenticationManager; //занимается проверкой
    private UserDetailsService userDetailsService;
    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            userDetailsService.loadUserByUsername(authRequest.getUsername());
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Неправильный логин или пароль"),
                    HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
