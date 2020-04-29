package com.bookmyticket.api;

import java.time.Duration;

public class Movie {
    private final String id;
    private String name;
    private final Duration duration;

    public Movie(String id, String name, Duration duration) {
        this.id = id;
        this.name = name;
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Duration getDuration() {
        return duration;
    }
}
