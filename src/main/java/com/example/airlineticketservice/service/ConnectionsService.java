package com.example.airlineticketservice.service;

import com.example.airlineticketservice.entity.Airport;
import com.example.airlineticketservice.entity.Connections;

import java.sql.Timestamp;
import java.util.List;

public interface ConnectionsService {
    public List<Connections> findAll();
    public List<Airport> findAllByCity();
    public List<Airport> findDistinctBy();
    public List<Connections> findByOriginAirport(Airport originAirport);

    public List<Airport> findDestAirportByOrigAirport(Airport originAirport);

    public List<Timestamp> findFlightDateByOrigAndDestAirport(Airport origAirport, Airport destAirport);

    public List<Connections> findFlightDateByOriginAirportAndDestinationAirportAndDepartureDay(Airport origAirport, Airport destAirport, String departureDay);

}
