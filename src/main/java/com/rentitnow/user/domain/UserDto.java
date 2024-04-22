package com.rentitnow.user.domain;

import java.time.LocalDate;

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
