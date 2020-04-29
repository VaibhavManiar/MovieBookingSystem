package com.bookmyticket.api;

import java.time.LocalDateTime;
import java.util.Objects;

public class MovieShow {
    private final String showNumber;
    private final String movieId;
    private final LocalDateTime startTime;
    private final MovieHall movieHall;
    private Double price;

    public MovieShow(String showNumber, LocalDateTime startTime, String movieId, MovieHall movieHall, Double price) {
        this.showNumber = showNumber;
        this.startTime = startTime;
        this.movieId = movieId;
        this.movieHall = movieHall;
        this.price = price;
    }

    public String getShowNumber() {
        return showNumber;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public String getMovieId() {
        return movieId;
    }

    public MovieHall getMovieHall() {
        return movieHall;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovieShow)) return false;
        MovieShow movieShow = (MovieShow) o;
        return Objects.equals(showNumber, movieShow.showNumber) &&
                Objects.equals(movieId, movieShow.movieId) &&
                Objects.equals(startTime, movieShow.startTime) &&
                Objects.equals(movieHall, movieShow.movieHall);
    }

    @Override
    public int hashCode() {
        return Objects.hash(showNumber, movieId, startTime, movieHall);
    }
}
