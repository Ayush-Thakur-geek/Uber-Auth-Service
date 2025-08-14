package com.uber.UberAuthService.controllers;

import com.uber.UberAuthService.dtos.PassengerDto;
import com.uber.UberAuthService.dtos.PassengerSignUpRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @PostMapping("/signup/passenger")
    public ResponseEntity<PassengerDto> signUpPassenger(@RequestBody PassengerSignUpRequestDto requestDto) {

        return null;
    }
}
