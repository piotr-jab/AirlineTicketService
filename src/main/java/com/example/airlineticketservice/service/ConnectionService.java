package com.example.airlineticketservice.service;

import com.example.airlineticketservice.entity.Airport;
import com.example.airlineticketservice.entity.Connection;

import java.sql.Timestamp;
import java.util.List;

public interface ConnectionService {
    public List<Connection> findAll();
    public List<Airport> findAllByCity();
    public List<Airport> findDistinctBy();
    public List<Connection> findByOriginAirport(Airport originAirport);

    public List<Airport> findDestAirportByOrigAirport(Airport originAirport);

    public List<Timestamp> findFlightDateByOrigAndDestAirport(Airport origAirport, Airport destAirport);

    public List<Connection> findFlightDateByOriginAirportAndDestinationAirportAndDepartureDay(Airport origAirport, Airport destAirport, String departureDay);

}
