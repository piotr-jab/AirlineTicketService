package com.example.airlineticketservice.controller;

import com.example.airlineticketservice.entity.Airport;
import com.example.airlineticketservice.entity.Connection;
import com.example.airlineticketservice.service.AirportService;
import com.example.airlineticketservice.service.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/connections")
public class ConnectionController {
    private ConnectionService connectionService;
    private AirportService airportService;

    @Autowired
    public ConnectionController(ConnectionService connectionService, AirportService airportService) {
        this.connectionService = connectionService;
        this.airportService = airportService;
    }

    @GetMapping("/search")
    public String listConnections(Model model) {
        List<Airport> connectionsList = connectionService.findDistinctBy();
        model.addAttribute("connections", connectionsList);

        return "connections-search";
    }

    @GetMapping("/getDestinationAirport")
    public @ResponseBody List<Airport> getDestinationAirports(@RequestParam("originCityName") String originAirportIcao) {
        List<Airport> destinationAirports = connectionService.findDestAirportByOrigAirport(airportService.findByIcao(originAirportIcao));
        return destinationAirports;
    }

    //for deletion if getDestinationAirport works
    @GetMapping("/getDestinationCity")
    public @ResponseBody List<String> getDestinationCity(@RequestParam("originCityName") String originCityName) {
        List<Airport> originAirports = connectionService.findDestAirportByOrigAirport(airportService.findByCity(originCityName));
        List<String> cities = new ArrayList<>();

        for (Airport tempAirport:originAirports) {
            cities.add(tempAirport.getCity());
        }

        return cities;
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

    @GetMapping("/select")
    public String confirm(@RequestParam("originAirportIcao") String originAirportIcao,
                          @RequestParam("destinationAirportIcao") String destinationAirportIcao,
                          @RequestParam("flightDate") String flightDate,
                          Model model) {

        Airport originAirportObject = airportService.findByIcao(originAirportIcao);
        Airport destinationAirportObject = airportService.findByIcao(destinationAirportIcao);
        List<Connection> connections = connectionService.findFlightDateByOriginAirportAndDestinationAirportAndDepartureDay(originAirportObject, destinationAirportObject, flightDate);

        List<List<String>> flightTimes = new ArrayList<>();
        String dateFormat = "HH:mm";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);

        for (Connection tempConnection :connections) {
            flightTimes.add(new ArrayList<>(){
                {
                    add(formatter.format(tempConnection.getDepartureDate().toLocalDateTime()));
                    add(formatter.format(tempConnection.getArrivalDate().toLocalDateTime()));
                }
            });
        }

        model.addAttribute("originAirportCity", originAirportObject.getCity());
        model.addAttribute("destinationAirportCity", destinationAirportObject.getCity());
        model.addAttribute("originAirportIcao", originAirportObject.getIcao());
        model.addAttribute("destinationAirportIcao", destinationAirportObject.getIcao());
        model.addAttribute("flightDate", flightDate);
        model.addAttribute("flightTimes", flightTimes);
        return "selection";
    }


    @PostMapping("/confirm")
    public String showConfirmationPage(Model model,
                                       @RequestParam("originAirportCity") String originAirportCity,
                                       @RequestParam("destinationAirportCity") String destinationAirportCity,
                                       @RequestParam("flightDate") String flightDate,
                                       @RequestParam("selectedFlightTime") String selectedFlightTime,
                                       @RequestParam("originAirportIcao") String originAirportIcao,
                                       @RequestParam("destinationAirportIcao") String destinationAirportIcao) {

        // Pass the received data to the confirmation page
        model.addAttribute("originAirportCity", originAirportCity);
        model.addAttribute("destinationAirportCity", destinationAirportCity);
        model.addAttribute("flightDate", flightDate);
        model.addAttribute("selectedFlightTime", selectedFlightTime);
        model.addAttribute("originAirportIcao", originAirportIcao);
        model.addAttribute("destinationAirportIcao", destinationAirportIcao);


        return "confirmation";
    }
}
