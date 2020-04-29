package com.bookmyticket.api;

public class Seat {
    private final int row;
    private final char column;
    private boolean isAvailable;

    public Seat(int row, char column, boolean isAvailable) {
        this.row = row;
        this.column = column;
    }

    public String getSeatNumber() {
        return this.row + String.valueOf(this.column);
    }

    public int getRow() {
        return row;
    }

    public char getColumn() {
        return column;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
