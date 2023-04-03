package com.example.airlineticketservice.dao;

import com.example.airlineticketservice.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AirportRepository extends JpaRepository<Airport, String> {
    public List<Airport> findAllByOrderByCityAsc();

    public Airport findByCity(String city);

    public Airport findByIcao(String icao);

}
