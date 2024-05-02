package com.rentitnow.user.domain;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserDto(
        Long userId,
        String email,
        String password,
        String firstname,
        String lastname,
        String phoneNumber,
        LocalDate birthDate,
        LocalDate creationDate
) {
}
