package com.rentitnow.rent.service;

import com.rentitnow.cart.domain.Cart;
import com.rentitnow.movie.domain.Movie;
import com.rentitnow.rent.controller.RentNotFoundException;
import com.rentitnow.rent.domain.Rent;
import com.rentitnow.rent.repository.RentRepository;
import com.rentitnow.transaction.domain.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentService {

    private final RentRepository rentRepository;

    public void rentMovie(final Cart cart, final Transaction transaction) {
        for (Movie movie: cart.getMovies()) {
            rentRepository.save(Rent.builder()
                    .movie(movie)
                    .cost(movie.getPrice())
                    .rentDate(LocalDate.now())
                    .returnDate(LocalDate.now().plusDays(14))
                    .user(cart.getUser())
                    .transaction(transaction)
                    .build());
        }
    }

    public Rent getRent(final Long rentID) throws RentNotFoundException {
        return rentRepository.findById(rentID).orElseThrow(RentNotFoundException::new);
    }

    public List<Rent> getAllRents() {
        return rentRepository.findAll();
    }


    public List<Rent> getAllUserRents(final Long userId) {
        List<Rent> userRents = rentRepository.findAll();
        userRents.stream()
                .filter(u -> u.getUser().getUserId().equals(userId))
                .toList();
        return userRents;
    }
}
