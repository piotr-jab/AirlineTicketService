package com.example.airlineticketservice.service;

import com.example.airlineticketservice.entity.Connection;
import com.example.airlineticketservice.entity.Seat;
import com.example.airlineticketservice.entity.User;

import java.util.List;

public interface SeatService {
    List<Seat> findAllSeatsByUser(User user);

    List<Seat> findSeatsByConnection(Connection connection);

    List<List<List<String>>> getSeatNumbering(Connection connection);

    void save(Seat seat);

}
