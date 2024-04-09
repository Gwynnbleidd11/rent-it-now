package com.rentitnow.rent.service;

import com.rentitnow.rent.controller.RentNotFoundException;
import com.rentitnow.rent.domain.Rent;
import com.rentitnow.rent.repository.RentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RentService {

    private RentRepository rentRepository;

    public Rent rentMovie(final Rent rent) {
        return rentRepository.save(rent);
    }

    public Rent getRent(final UUID rentID) throws RentNotFoundException {
        return rentRepository.findById(rentID).orElseThrow(RentNotFoundException::new);
    }
}
