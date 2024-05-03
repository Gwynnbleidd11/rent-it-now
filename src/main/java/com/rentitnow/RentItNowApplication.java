package com.rentitnow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class RentItNowApplication {

    public static void main(String[] args) {
        SpringApplication.run(RentItNowApplication.class, args);
    }

}
