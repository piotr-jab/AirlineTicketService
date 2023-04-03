package com.example.airlineticketservice.entity;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;

@Entity
@Table(name="connections")
public class Connections {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "destination_airport")
    private Airport destinationAirport;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "origin_airport")
    private Airport originAirport;

    @Column(name="flight_date")
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    private Timestamp flightDate;

    public Connections() {
    }

    public Connections(String id, Airport destinationAirport, Airport originAirport, Timestamp flightDate) {
        this.id = id;
        this.destinationAirport = destinationAirport;
        this.originAirport = originAirport;
        this.flightDate = flightDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Airport getDestinationAirport() {
        return destinationAirport;
    }

    public void setDestinationAirport(Airport destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    public Airport getOriginAirport() {
        return originAirport;
    }

    public void setOriginAirport(Airport originAirport) {
        this.originAirport = originAirport;
    }

    public Timestamp getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(Timestamp flightDate) {
        this.flightDate = flightDate;
    }
}

