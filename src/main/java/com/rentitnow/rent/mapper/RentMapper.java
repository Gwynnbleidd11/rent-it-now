package com.rentitnow.rent.mapper;

import com.rentitnow.rent.domain.Rent;
import com.rentitnow.rent.domain.RentDto;
import com.rentitnow.user.controller.UserNotFoundException;
import com.rentitnow.user.domain.User;
import com.rentitnow.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class RentMapper {

    private UserService userService;

    public Rent mapToRent(final RentDto rentDTO) throws UserNotFoundException {
        User user = userService.getUser(rentDTO.userId());
        return Rent.builder()
                .rentId(rentDTO.id())
                .cost(rentDTO.cost())
                .rentDate(rentDTO.rentDate())
                .returnDate(rentDTO.returnDate())
                .user(user)
                .build();
    }

    public RentDto mapToRentDTO(final Rent rent) {
        return new RentDto(rent.getRentId(), rent.getCost(),
                rent.getRentDate(), rent.getReturnDate(), rent.getUser().getUserId());
    }
}
