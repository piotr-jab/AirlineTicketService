package com.example.airlineticketservice.entity;

import java.io.Serializable;

public class SeatId implements Serializable {
    private Connection connection;
    private String seatNumber;


    public SeatId() {
    }

    public SeatId(Connection connection, String seatNumber) {
        this.connection = connection;
        this.seatNumber = seatNumber;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }
}
