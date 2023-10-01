package com.example.airlineticketservice.service;

import com.example.airlineticketservice.entity.Airport;
import com.example.airlineticketservice.entity.Connection;

import java.sql.Timestamp;
import java.util.List;

public interface ConnectionService {
    List<Connection> findAll();
    List<Airport> findAllByCity();
    List<Airport> findDistinctBy();
    List<Connection> findByOriginAirport(Airport originAirport);

    List<Airport> findDestAirportByOrigAirport(Airport originAirport);

    List<Timestamp> findFlightDateByOrigAndDestAirport(Airport origAirport, Airport destAirport);

    List<Connection> findByOriginAirportAndDestinationAirportAndDepartureDay(Airport origAirport, Airport destAirport, String departureDay);

    Connection findById(String id);

}
