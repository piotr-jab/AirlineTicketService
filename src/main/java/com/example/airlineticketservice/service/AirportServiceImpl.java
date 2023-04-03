package com.example.airlineticketservice.service;

import com.example.airlineticketservice.dao.AirportRepository;
import com.example.airlineticketservice.entity.Airport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirportServiceImpl implements AirportService{
    private AirportRepository airportRepository;
    @Autowired
    public AirportServiceImpl(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    @Override
    public List<Airport> findAll() {
        return this.airportRepository.findAllByOrderByCityAsc();
    }

    public Airport findByCity(String city) {
        return this.airportRepository.findByCity(city);
    };

    public Airport findByIcao(String icao) {
        return this.airportRepository.findByIcao(icao);
    }

}
