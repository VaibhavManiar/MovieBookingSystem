package com.bookmyticket.service;

import com.bookmyticket.api.MovieShow;
import com.bookmyticket.api.Seat;

import java.util.Set;

public interface MovieShowSearchService {
    Set<MovieShow> getMovieShows(String movieName);
    Set<Seat> getAvailableSeats(String showNumber);
}
