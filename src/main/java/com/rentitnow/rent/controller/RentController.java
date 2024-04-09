package com.rentitnow.rent.controller;

import com.rentitnow.rent.mapper.RentMapper;
import com.rentitnow.rent.service.RentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rents")
@RequiredArgsConstructor
@CrossOrigin("*")
public class RentController {

    private final RentService rentService;
    private final RentMapper rentMapper;

}
