package com.uber.UberAuthService.services;

import com.uber.UberAuthService.dtos.PassengerDto;
import com.uber.UberAuthService.dtos.PassengerSignUpRequestDto;
import com.uber.UberAuthService.models.Passenger;
import com.uber.UberAuthService.repositories.PassengerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final PassengerRepository passengerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    AuthService(PassengerRepository passengerRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.passengerRepository = passengerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public PassengerDto signUpPassenger(PassengerSignUpRequestDto requestDto) {
        Passenger passenger = Passenger.builder()
                .name(requestDto.getName())
                .email(requestDto.getEmail())
                .phoneNumber(requestDto.getPhoneNumber())
                .password(bCryptPasswordEncoder.encode(requestDto.getPassword()))
                .build();

        System.out.println(passenger);

        try {
            Passenger savedPassenger = passengerRepository.save(passenger);
            return PassengerDto.fromPassenger(savedPassenger);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
