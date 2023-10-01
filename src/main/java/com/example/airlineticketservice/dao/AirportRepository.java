package com.example.airlineticketservice.dao;

import com.example.airlineticketservice.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AirportRepository extends JpaRepository<Airport, String> {
    List<Airport> findAllByOrderByCityAsc();

    Airport findByCity(String city);

    Airport findByIcao(String icao);

}
