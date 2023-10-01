package com.example.airlineticketservice.service;

import com.example.airlineticketservice.dao.SeatRepository;
import com.example.airlineticketservice.entity.Connection;
import com.example.airlineticketservice.entity.Seat;
import com.example.airlineticketservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class SeatServiceImpl implements SeatService{

    private final SeatRepository seatRepository;

    @Value("classpath:seats.txt")
    private Resource resource;

    @Autowired
    public SeatServiceImpl(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    private List<String> loadSeatsNumbering() {
        try {
            Resource resource = new ClassPathResource("seats.txt");
            File file = resource.getFile();
            return Files.readAllLines(file.toPath());
        } catch (IOException e) {
            // Handle the exception appropriately
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private List<String> convertSeatToString(List<Seat> seats) {
        List<String> sublist = new ArrayList<>();

        for (Seat seat : seats) {
            sublist.add(seat.getSeatNumber());
        }

        return sublist;
    }

    private List<List<String>> addSeatOccupancyData(List<String> seatNumbering, List<Seat> takenSeats) {
        List<List<String>> listWithOccupancy = new ArrayList<>();
        List<String> sublist = new ArrayList<>();
        List<String> convertedSeatNumbers = convertSeatToString(takenSeats);

        for (String seat : seatNumbering) {
            if (convertedSeatNumbers.contains(seat)) {
                sublist.add(seat);
                sublist.add("true");
                listWithOccupancy.add(sublist);
                sublist = new ArrayList<>();
            } else {
                sublist.add(seat);
                sublist.add("false");
                listWithOccupancy.add(sublist);
                sublist = new ArrayList<>();
            }
        }

        return listWithOccupancy;
    }

    public List<List<List<String>>> convertToNestedList(List<List<String>> originalList) {
        List<List<List<String>>> finalList = new ArrayList<>();
        List<List<String>> subList = new ArrayList<>();

        for (List<String> value : originalList) {
            if (value.get(0).equals("newline")) {
                finalList.add(subList);
                subList = new ArrayList<>();
            } else {
                subList.add(value);
            }
        }

        //add the last sublist if there are remaining elements
        if (!subList.isEmpty()) {
            finalList.add(subList);
        }

        return finalList;
    }

    @Override
    public List<Seat> findAllSeatsByUser(User user) {
        return seatRepository.findAllByPassenger(user);
    }

    @Override
    public List<Seat> findSeatsByConnection(Connection connection) {
        return seatRepository.findSeatsByConnection(connection);
    }

    @Override
    public List<List<List<String>>> getSeatNumbering(Connection connection) {
        return convertToNestedList(addSeatOccupancyData(loadSeatsNumbering(), findSeatsByConnection(connection)));
    }

    @Override
    public void save(Seat seat) {
        seatRepository.save(seat);
    }

}
