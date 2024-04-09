package com.rentitnow.user.domain;

import java.time.LocalDate;
import java.util.UUID;

public record UserDTO(
        UUID id,
        String email,
        String password,
        String firstname,
        String lastname,
        String phoneNumber,
        LocalDate birthDate,
        LocalDate creationDate
) {
}
