package com.example.airlineticketservice.controller;

import com.example.airlineticketservice.entity.*;
import com.example.airlineticketservice.service.AirportService;
import com.example.airlineticketservice.service.ConnectionService;
import com.example.airlineticketservice.service.SeatService;
import com.example.airlineticketservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("/connections")
public class ConnectionController {
    private final ConnectionService connectionService;
    private final AirportService airportService;
    private final SeatService seatService;
    private final UserService userService;

    @Autowired
    public ConnectionController(ConnectionService connectionService,
                                AirportService airportService,
                                SeatService seatService,
                                UserService userService) {
        this.connectionService = connectionService;
        this.airportService = airportService;
        this.seatService = seatService;
        this.userService = userService;
    }

    @GetMapping("/search")
    public String listConnections(Model model) {
        List<Airport> connectionsList = connectionService.findDistinctBy();
        model.addAttribute("connections", connectionsList);

        return "connections-search";
    }

    @GetMapping("/getDestinationAirport")
    public @ResponseBody List<Airport> getDestinationAirports(@RequestParam("originCityName") String originAirportIcao) {
        return connectionService.findDestAirportByOrigAirport(airportService.findByIcao(originAirportIcao));
    }

    @GetMapping("/getFlightDates")
    public @ResponseBody List<String> getFlightDates(@RequestParam("origAirportIcao") String origAirportIcao, @RequestParam("destAirportIcao") String destAirportIcao) {
        Airport origAirportObject = airportService.findByIcao(origAirportIcao);
        Airport destAirportObject = airportService.findByIcao(destAirportIcao);
        List<Timestamp> flightTimes = connectionService.findFlightDateByOrigAndDestAirport(origAirportObject, destAirportObject);
        List<String> result = new ArrayList<>();
        String dateFormat = "yyy-MM-dd";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);

        for (Timestamp tempTime:flightTimes) {
            result.add(formatter.format(tempTime.toLocalDateTime()));
        }
        return result;
    }

    @GetMapping("/selectFlight")
    public String showFlightSelectionPage(Model model,
                                          @RequestParam("originAirportIcao") String originAirportIcao,
                                          @RequestParam("destinationAirportIcao") String destinationAirportIcao,
                                          @RequestParam("flightDate") String flightDate
                                          ) {

        Airport originAirportObject = airportService.findByIcao(originAirportIcao);
        Airport destinationAirportObject = airportService.findByIcao(destinationAirportIcao);
        List<Connection> connections = connectionService.findByOriginAirportAndDestinationAirportAndDepartureDay(originAirportObject, destinationAirportObject, flightDate);

        model.addAttribute("connections", connections);
        return "flight-selection";
    }

    @PostMapping("/selectSeat")
    public String showSeatSelectionPage(Model model,
                                        @RequestParam("connectionId") String connectionId,
                                        @RequestParam("selectedSeatPreviously") Optional<String> selectedSeatPreviously) {

        Connection connection = connectionService.findById(connectionId);
        model.addAttribute("seatArrangement", seatService.getSeatNumbering(connection));
        model.addAttribute("connection", connection);
        model.addAttribute("selectedSeatPreviously", selectedSeatPreviously.orElse("empty"));
        return "seat-selection";
    }

    @PostMapping("/confirm")
    public String showConfirmationPage(Model model,
                                       @RequestParam("connectionId") String connectionId,
                                       @RequestParam("selectedSeat") String selectedSeat){

        Connection connection = connectionService.findById(connectionId);

        model.addAttribute("connection", connection);
        model.addAttribute("selectedSeat", selectedSeat);
        return "confirmation";
    }

    @PostMapping("/bookFlight")
    public String bookFlight(Model model,
                             @RequestParam("connectionId") String connectionId,
                             @RequestParam("seatNumber") String seatNumber){

        Connection connection = connectionService.findById(connectionId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String passenger = authentication.getName();

        //check if seat number is free
        List<Seat> seats = seatService.findSeatsByConnection(connection);

        model.addAttribute("connection", connection);
        model.addAttribute("seatNumber", seatNumber);

        for (Seat seat : seats) {
            if(seat.getSeatNumber().equals(seatNumber)) {
                return "booking-failure";
            }
        }
        //find passenger and book seat (future check for eligibility)
        Optional<User> optionalUser = userService.findByUsername(passenger);
        return optionalUser.map(user -> {
            seatService.save(new Seat(new SeatId(connection, seatNumber), connection, seatNumber, user));
            return "booking-success";
        }).orElse("booking-failure");

    }


    @GetMapping("/userPage")
    public String showUserPage(Model model
    ) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Optional<User> optionalUser = userService.findByUsername(username);
        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<Seat> seats = seatService.findAllSeatsByUser(user);
        seats.sort(Comparator.comparing(seat -> seat.getConnection().getDepartureDate()));

        model.addAttribute("seats", seats);

        return "user-page";
    }

    @GetMapping("/login")
    public String viewLoginPage(){
        return "login-form";
    }
}
