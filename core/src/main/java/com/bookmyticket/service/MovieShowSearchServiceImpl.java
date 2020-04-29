package com.bookmyticket.service;

import com.bookmyticket.api.Movie;
import com.bookmyticket.api.MovieShow;
import com.bookmyticket.api.Seat;
import com.bookmyticket.db.MovieDB;
import com.bookmyticket.db.MovieHallAndMovieMapping;
import com.bookmyticket.db.MovieHallDB;
import com.bookmyticket.db.MovieSeatHoldCache;

import java.util.Set;
import java.util.stream.Collectors;

public class MovieShowSearchServiceImpl implements MovieShowSearchService {

    private final MovieDB movieDB;
    private final MovieHallDB movieHallDB;
    private final MovieHallAndMovieMapping movieHallAndMovieMappin;
    private final MovieSeatHoldCache movieSeatHoldCache;


    public MovieShowSearchServiceImpl(MovieDB movieDB, MovieHallDB movieHallDB, MovieHallAndMovieMapping movieHallAndMovieMappin, MovieSeatHoldCache movieSeatHoldCache) {
        this.movieDB = movieDB;
        this.movieHallDB = movieHallDB;
        this.movieHallAndMovieMappin = movieHallAndMovieMappin;
        this.movieSeatHoldCache = movieSeatHoldCache;
    }

    @Override
    public Set<MovieShow> getMovieShows(String movieName) {
        Movie movie = this.movieDB.getMovieByName(movieName);
        return this.movieHallAndMovieMappin.getMovieShows(movie.getId());
    }

    @Override
    public Set<Seat> getAvailableSeats(String showNumber) {
        Set<Seat> seats = this.movieHallAndMovieMappin.getSeats(showNumber);
        return seats.stream().filter(Seat::isAvailable).filter(seat ->
                movieSeatHoldCache.isSeatHoldedForNext30Sec(showNumber, seat.getSeatNumber())).
                collect(Collectors.toSet());
    }
}
