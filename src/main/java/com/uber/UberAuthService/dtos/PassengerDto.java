package com.uber.UberAuthService.dtos;

import com.uber.UberEntityService.models.Passenger;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PassengerDto {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private Date createdAt;

    public static PassengerDto fromPassenger(Passenger passenger) {
        return PassengerDto.builder()
                .id(passenger.getId())
                .name(passenger.getName())
                .email(passenger.getEmail())
                .phoneNumber(passenger.getPhoneNumber())
                .password(passenger.getPassword())
                .createdAt(passenger.getCreatedAt())
                .build();
    }
}
