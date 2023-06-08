package com.example.airlineticketservice.service;

import com.example.airlineticketservice.dao.SeatRepository;
import com.example.airlineticketservice.entity.Connection;
import com.example.airlineticketservice.entity.Seat;
import com.example.airlineticketservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatServiceImpl implements SeatService{

    private SeatRepository seatRepository;

    @Autowired
    public SeatServiceImpl(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    @Override
    public List<Seat> findAllByPassenger(String username) {
        return seatRepository.findAllByPassenger(username);
    }

    @Override
    public List<Seat> findSeatsByConnection(Connection connection) {
        return seatRepository.findSeatsByConnection(connection);
    }
}
