package com.uber.UberAuthService.controllers;

import com.uber.UberAuthService.dtos.AuthRequestDto;
import com.uber.UberAuthService.dtos.PassengerDto;
import com.uber.UberAuthService.dtos.PassengerSignUpRequestDto;
import com.uber.UberAuthService.services.AuthService;
import com.uber.UberAuthService.services.JwtService;
import com.uber.UberAuthService.services.UserDetailsServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/auth")
@Log4j2
public class AuthController {

    @Value("${cookie.expiry}")
    private int cookieExpire;

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    AuthController(
            AuthService authService,
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/signup/passenger")
    public ResponseEntity<PassengerDto> signUpPassenger(@RequestBody PassengerSignUpRequestDto requestDto, HttpServletResponse response) {
        PassengerDto passengerDto = authService.signUpPassenger(requestDto);
        System.out.println(passengerDto);
        if (passengerDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String token = jwtService.createToken(requestDto.getEmail());
        ResponseCookie cookie = ResponseCookie.from("JwtToken", token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(cookieExpire)
                .build();
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return new ResponseEntity<>(passengerDto, HttpStatus.CREATED);
    }

    @PostMapping("/signin/passenger")
    public ResponseEntity<?> signInPassenger(
            @RequestBody AuthRequestDto authRequestDto,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        log.info("Hey, i am here");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequestDto.getUsername(), authRequestDto.getPassword()
                )
        );

        if (authentication.isAuthenticated()) {
            System.out.println(authentication.getName());

            String token = jwtService.createToken(authentication.getName());
            ResponseCookie cookie = ResponseCookie.from("JwtToken", token)
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(cookieExpire)
                    .build();
            response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            return new ResponseEntity<>("Successful Authentication", HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validatePassenger(HttpServletRequest request, Principal principal) {
        System.out.println("Inside validate controller");
        Cookie[] cookies = request.getCookies();
        String token = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("JwtToken")) {
                token = cookie.getValue();
                System.out.println(token);
            }
        }
        if (token == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        boolean valid = jwtService.validateToken(token, principal.getName());
        if (!valid) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(request.getHeader("Cookie"), HttpStatus.OK);
    }
}
