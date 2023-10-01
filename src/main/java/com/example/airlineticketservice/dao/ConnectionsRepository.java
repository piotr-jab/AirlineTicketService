package com.example.airlineticketservice.dao;


import com.example.airlineticketservice.entity.Airport;
import com.example.airlineticketservice.entity.Connection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface ConnectionsRepository extends JpaRepository<Connection, Integer> {

    @Query("SELECT DISTINCT c.originAirport FROM Connection c ORDER BY c.originAirport.city")
    List<Airport> findDistinctOriginAirport();

    List<Connection> findByOriginAirport(Airport originAirport);

    @Query("SELECT c.destinationAirport FROM Connection c WHERE c.originAirport = :originAirport AND c.destinationAirport != :originAirport ORDER BY c.destinationAirport.city")
    List<Airport> findDestAirportByOrigAirport(@Param("originAirport") Airport originAirport);

    @Query("SELECT c.departureDate FROM Connection c WHERE c.originAirport = :origAirport AND c.destinationAirport = :destAirport")
    List<Timestamp> findFlightDateByOrigAndDestAirport(@Param("origAirport") Airport origAirport, @Param("destAirport") Airport destAirport);

    @Query("SELECT c FROM Connection c WHERE c.originAirport = :origAirport AND " +
            "c.destinationAirport = :destAirport AND CAST(c.departureDate AS DATE) = CAST(:departureDay AS DATE) ORDER BY c.departureDate")
    List<Connection> findByOriginAirportAndDestinationAirportAndDepartureDay(@Param("origAirport") Airport origAirport,
                                                                                       @Param("destAirport") Airport destAirport,
                                                                                       @Param("departureDay") String departureDay);
    Connection findConnectionById(String id);

}
