package com.rentitnow.user.domain;

import com.rentitnow.rent.domain.Rent;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userId;
    @NotNull
    private String email;
    @NotNull
    private String password;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private LocalDate birthDate;
    private LocalDate creationDate;
    @OneToMany(
            targetEntity = Rent.class,
            mappedBy = "user",
            fetch = FetchType.LAZY
    )
    private List<Rent> listOfRents;
}
