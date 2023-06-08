package com.example.airlineticketservice.service;

import com.example.airlineticketservice.entity.Connection;
import com.example.airlineticketservice.entity.Seat;
import com.example.airlineticketservice.entity.User;

import java.util.List;

public interface SeatService {
    public List<Seat> findAllByPassenger(String username);

    public List<Seat> findSeatsByConnection(Connection connection);

}
