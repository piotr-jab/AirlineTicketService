package com.example.airlineticketservice.service;

import com.example.airlineticketservice.entity.Airport;

import java.util.List;

public interface AirportService {
    List<Airport> findAll();

    Airport findByIcao(String icao);

    Airport findByCity(String city);

}
