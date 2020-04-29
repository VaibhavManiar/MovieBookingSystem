package com.bookmyticket.db;

import com.bookmyticket.api.Screen;

import java.util.Map;
import java.util.Set;

public class MovieVsScreenMapping {
    Map<String, Set<Screen>> movieVsScreenMapping;

    public Set<Screen> getScreen(String movieId) {
        return movieVsScreenMapping.get(movieId);
    }

    public void addMovie(String movieId, Set<Screen> screens) {
        this.movieVsScreenMapping.put(movieId, screens);
    }
}
