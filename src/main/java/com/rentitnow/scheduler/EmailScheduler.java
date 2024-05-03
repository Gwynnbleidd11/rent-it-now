package com.rentitnow.scheduler;

import com.rentitnow.config.AdminConfig;
import com.rentitnow.mail.Mail;
import com.rentitnow.mail.service.SimpleEmailService;
import com.rentitnow.rent.domain.Rent;
import com.rentitnow.rent.repository.RentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EmailScheduler {

    private static final String SUBJECT = "Daily report - rented movies";
    private final SimpleEmailService simpleEmailService;
    private final RentRepository rentRepository;
    private final AdminConfig adminConfig;

    @Scheduled(cron = "0 0 6 * * *")
    public void sendMoviesRentedPastDayReport() {
        List<Rent> rentsList = rentRepository.findAll().stream()
                .filter(rent -> rent.getRentDate().atStartOfDay().isAfter(LocalDateTime.now().minusDays(1)))
                .toList();
        int rentsCount = rentsList.size();
        BigDecimal sum = rentsList.stream().
                map(Rent::getCost)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
        simpleEmailService.sendRentsReport(
                Mail.builder()
                        .mailTo(adminConfig.getAdminMail())
                        .subject(SUBJECT)
                        .message(rentsCount + " has been rented yesterday, for the total amount of: " + sum)
                        .build()
        );
    }
}
