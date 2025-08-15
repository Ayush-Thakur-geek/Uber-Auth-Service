package com.uber.UberAuthService.controllers;

import com.uber.UberAuthService.dtos.PassengerDto;
import com.uber.UberAuthService.dtos.PassengerSignUpRequestDto;
import com.uber.UberAuthService.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup/passenger")
    public ResponseEntity<PassengerDto> signUpPassenger(@RequestBody PassengerSignUpRequestDto requestDto) {
        PassengerDto passengerDto = authService.signUpPassenger(requestDto);
        System.out.println(passengerDto);
        if (passengerDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(passengerDto, HttpStatus.CREATED);
    }
}
