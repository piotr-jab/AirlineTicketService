package com.example.airlineticketservice.entity;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;

@Entity
@Table(name="connections")
public class Connection {
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

    @Column(name="departure_date")
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    private Timestamp departureDate;

    @Column(name="arrival_date")
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    private Timestamp arrivalDate;

    public Connection() {
    }

    public Connection(String id, Airport destinationAirport, Airport originAirport, Timestamp departureDate, Timestamp arrivalDate) {
        this.id = id;
        this.destinationAirport = destinationAirport;
        this.originAirport = originAirport;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
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

    public Timestamp getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Timestamp departureDate) {
        this.departureDate = departureDate;
    }

    public Timestamp getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Timestamp arrivalDate) {
        this.arrivalDate = arrivalDate;
    }
}

