package com.bookmyticket.db;

import com.bookmyticket.api.Movie;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MovieDB {
    Map<String, Movie> movies = new ConcurrentHashMap<>();

    public void setMovie(Movie movie) {
        this.movies.put(movie.getId(), movie);
    }

    public Movie getMovieByName(String movieName) {
        return this.movies.get(movieName);
    }

}
