package com.example.airlineticketservice.entity;

import jakarta.persistence.*;

@Entity
@IdClass(SeatId.class)
@Table(name="seats")
public class Seat {

    @Id
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "connection")
    private Connection connection;

    @Id
    @Column(name = "seat_number")
    private String seatNumber;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "passenger")
    private User passenger;

    public Seat() {
    }

    public Seat(SeatId seatId, Connection connection, String seatNumber, User passenger) {
        this.connection = connection;
        this.seatNumber = seatNumber;
        this.passenger = passenger;
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

    public User getPassenger() {
        return passenger;
    }

    public void setPassenger(User passenger) {
        this.passenger = passenger;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "connection=" + connection +
                ", seatNumber='" + seatNumber + '\'' +
                ", passenger=" + passenger +
                '}';
    }
}
