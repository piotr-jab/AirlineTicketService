package com.example.airlineticketservice.service;

import com.example.airlineticketservice.entity.Airport;

import java.util.List;

public interface AirportService {
    public List<Airport> findAll();

    public Airport findByIcao(String icao);

    public Airport findByCity(String city);

}
