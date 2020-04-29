package com.bookmyticket.db;

import com.bookmyticket.api.Movie;
import com.bookmyticket.api.MovieHall;
import com.bookmyticket.api.Screen;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MovieHallDB {
    Map<Screen, MovieHall> movieHalls = new ConcurrentHashMap<>();

    public void setMovie(MovieHall movieHall) {
        this.movieHalls.put(movieHall.getScreen(), movieHall);
    }

    public void getMovie(Screen screen) {
        this.movieHalls.get(screen);
    }
}
