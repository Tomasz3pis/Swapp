package com.swapp.application.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {

    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Token accessToken;
    private Token refreshToken;
}
