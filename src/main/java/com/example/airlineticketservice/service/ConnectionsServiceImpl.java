package com.example.airlineticketservice.service;

import com.example.airlineticketservice.dao.ConnectionsRepository;
import com.example.airlineticketservice.entity.Airport;
import com.example.airlineticketservice.entity.Connections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ConnectionsServiceImpl implements ConnectionsService{
    private ConnectionsRepository connectionsRepository;

    @Autowired
    public ConnectionsServiceImpl(ConnectionsRepository connectionsRepository) {
        this.connectionsRepository = connectionsRepository;
    }

    @Override
    public List<Connections> findAll() {
        return connectionsRepository.findAll();
    }

    @Override
    public List<Airport> findAllByCity() {
        return null;
    }

    @Override
    public List<Airport> findDistinctBy() {
        return connectionsRepository.findDistinctOriginAirport();
    }

    @Override
    public List<Connections> findByOriginAirport(Airport originAirport) {
        return connectionsRepository.findByOriginAirport(originAirport);
    }

    @Override
    public List<Airport> findDestAirportByOrigAirport(Airport originAirport) {
        return connectionsRepository.findDestAirportByOrigAirport(originAirport);
    }

    @Override
    public List<Timestamp> findDatesByOrigAndDestAirport(Airport origAirport, Airport destAirport) {
        return connectionsRepository.findDatesByOrigAndDestAirport(origAirport, destAirport);
    }
}
