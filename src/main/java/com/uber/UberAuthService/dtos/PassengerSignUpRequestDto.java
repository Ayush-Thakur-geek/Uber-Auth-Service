package com.uber.UberAuthService.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PassengerSignUpRequestDto {
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private Double rating;
}
