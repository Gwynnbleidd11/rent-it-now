package com.rentitnow.rent.mapper;

import com.rentitnow.rent.domain.Rent;
import com.rentitnow.rent.domain.RentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RentMapper {

    public RentDto mapToRentDto(final Rent rent) {
        return new RentDto(rent.getRentId(), rent.getMovie().getMovieId(), rent.getCost(),
                rent.getRentDate(), rent.getReturnDate(), rent.getUser().getUserId(),
                rent.getTransaction().getTransactionId());
    }

    public List<RentDto> mapToRentDtoList(final List<Rent> rentsList) {
        return rentsList.stream()
                .map(this::mapToRentDto)
                .toList();
    }
}
