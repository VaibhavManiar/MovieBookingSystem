package com.bookmyticket.db;

import com.bookmyticket.api.Screen;
import com.bookmyticket.api.MovieShow;
import com.bookmyticket.api.Seat;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MovieHallAndMovieMapping {

    Map<Screen, Map<String, Set<MovieShow>>> movieHallVsMovieMapping = new ConcurrentHashMap<>();

    Map<String, Set<MovieShow>> movieShowMap = new ConcurrentHashMap<>();

    Map<String, Set<Seat>> movieShowVsSeatMap = new ConcurrentHashMap<>();

    public void setMovieMapping(Screen screen, String movieId, Set<MovieShow> movieShows) {
        if(this.movieHallVsMovieMapping.containsKey(screen))  {
            this.movieHallVsMovieMapping.get(screen).put(movieId, movieShows);
        } else {
            Map<String, Set<MovieShow>> map = new ConcurrentHashMap<>();
            map.put(movieId, movieShows);
            this.movieHallVsMovieMapping.put(screen, map);
        }
    }

    public Set<MovieShow> getMovieTimings(Screen screen, String movieId){
        return this.movieHallVsMovieMapping.getOrDefault(screen, Collections.emptyMap()).get(movieId);
    }

    public Set<MovieShow> getMovieShows(String movieId) {
        return this.movieShowMap.getOrDefault(movieId, Collections.emptySet());
    }

    public Set<Seat> getSeats(String movieShow) {
        return this.movieShowVsSeatMap.get(movieShow);
    }
}
