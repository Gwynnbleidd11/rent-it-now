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

    @GetMapping("/rent/{rentId}")
    public ResponseEntity<RentDto> getRent(@PathVariable Long rentId) throws RentNotFoundException {
        Rent rent = rentService.getRent(rentId);
        return ResponseEntity.ok(rentMapper.mapToRentDto(rent));
    }

    @GetMapping(value = "/user/{userId}")
    public ResponseEntity<List<RentDto>> userRents(@PathVariable Long userId) {
        List<Rent> rentsList = rentService.getUserRents(userId);
        return ResponseEntity.ok(rentMapper.mapToRentDtoList(rentsList));
    }
}
