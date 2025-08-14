package com.uber.UberAuthService.dtos;

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
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private Date createdAt;
}
