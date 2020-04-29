package com.bookmyticket.service;

import com.bookmyticket.api.MovieShow;

import java.util.Set;

public interface MovieBookingService {
    void holdSeat(MovieShow movieShow, Set<String> seatNumbers, String userId);
    void bookSeat(MovieShow movieShow, Set<String> seatNumbers, String userId);
    void releaseSeats(MovieShow movieShow, Set<String> seatNumbers);
}
