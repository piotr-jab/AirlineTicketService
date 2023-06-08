package com.example.airlineticketservice.dao;

import com.example.airlineticketservice.entity.Connection;
import com.example.airlineticketservice.entity.Seat;
import com.example.airlineticketservice.entity.SeatId;
import com.example.airlineticketservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, SeatId> {

    public List<Seat> findAllByPassenger(String username);

    public List<Seat> findSeatsByConnection(Connection connection);
}
