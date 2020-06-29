package com.swapp.application.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.ZonedDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Token {

    private String value;
    private ZonedDateTime expiryDate;

    @JsonIgnore
    @NonNull
    private Long userId;
}
