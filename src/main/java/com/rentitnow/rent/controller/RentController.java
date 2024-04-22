package com.rentitnow.rent.controller;

import com.rentitnow.rent.domain.Rent;
import com.rentitnow.rent.domain.RentDto;
import com.rentitnow.rent.mapper.RentMapper;
import com.rentitnow.rent.service.RentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rents")
@RequiredArgsConstructor
@CrossOrigin("*")
public class RentController {

    private final RentService rentService;
    private final RentMapper rentMapper;

    @GetMapping
    public ResponseEntity<List<RentDto>> getAllRents() {
        List<Rent> rentsList = rentService.getAllRents();
        return ResponseEntity.ok(rentMapper.mapToRentDtoList(rentsList));
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<List<RentDto>> userRents(@PathVariable Long userId) {
        List<Rent> rentsList = rentService.getAllUserRents(userId);
        return ResponseEntity.ok(rentMapper.mapToRentDtoList(rentsList));
    }



}
