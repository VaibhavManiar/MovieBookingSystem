package com.bookmyticket.api;

import java.util.List;

public class MovieHall {
    private final Screen screen;
    private final List<Seat> seats;

    public MovieHall(Screen screen, List<Seat> seats) {
        this.screen = screen;
        this.seats = seats;
    }

    public Screen getScreen() {
        return screen;
    }

    public List<Seat> getSeats() {
        return seats;
    }
}
