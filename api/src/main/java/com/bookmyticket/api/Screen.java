package com.bookmyticket.api;

import java.util.Objects;

public class Screen {
    private final String number;

    public Screen(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Screen)) return false;
        Screen screen = (Screen) o;
        return number.equals(screen.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
