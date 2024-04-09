package com.rentitnow.rent.controller;

import com.rentitnow.rent.domain.Rent;
import com.rentitnow.rent.domain.RentDto;
import com.rentitnow.rent.mapper.RentMapper;
import com.rentitnow.rent.service.RentService;
import com.rentitnow.user.controller.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rents")
@RequiredArgsConstructor
@CrossOrigin("*")
public class RentController {

    private final RentService rentService;
    private final RentMapper rentMapper;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> rentMovie(@RequestBody RentDto rentDto) throws UserNotFoundException {
        Rent rent = rentMapper.mapToRent(rentDto);
        rentService.rentMovie(rent);
        return ResponseEntity.ok().build();
    }



}
