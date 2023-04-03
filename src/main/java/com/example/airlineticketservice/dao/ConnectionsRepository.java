package com.example.airlineticketservice.dao;


import com.example.airlineticketservice.entity.Airport;
import com.example.airlineticketservice.entity.Connections;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface ConnectionsRepository extends JpaRepository<Connections, Integer> {

    @Query("SELECT DISTINCT c.originAirport FROM Connections c ORDER BY c.originAirport.city")
    public List<Airport> findDistinctOriginAirport();

    public List<Connections> findByOriginAirport(Airport originAirport);

    @Query("SELECT c.destinationAirport FROM Connections c WHERE c.originAirport = :originAirport AND c.destinationAirport != :originAirport ORDER BY c.destinationAirport.city")
    public List<Airport> findDestAirportByOrigAirport(@Param("originAirport") Airport originAirport);

    @Query("SELECT c.flightDate FROM Connections c WHERE c.originAirport = :origAirport AND c.destinationAirport = :destAirport")
    public List<Timestamp> findDatesByOrigAndDestAirport(@Param("origAirport") Airport origAirport, @Param("destAirport") Airport destAirport);
}
