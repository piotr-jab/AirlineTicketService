package com.example.airlineticketservice.controller;

import com.example.airlineticketservice.entity.Airport;
import com.example.airlineticketservice.entity.Connections;
import com.example.airlineticketservice.service.AirportService;
import com.example.airlineticketservice.service.ConnectionsService;
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
public class ConnectionsController {
    private ConnectionsService connectionsService;
    private AirportService airportService;

    @Autowired
    public ConnectionsController(ConnectionsService connectionsService, AirportService airportService) {
        this.connectionsService = connectionsService;
        this.airportService = airportService;
    }

    @GetMapping("/search")
    public String listConnections(Model model) {
        List<Airport> connectionsList = connectionsService.findDistinctBy();
        model.addAttribute("connections", connectionsList);

        return "connections-search";
    }

    @GetMapping("/getDestinationAirport")
    public @ResponseBody List<Airport> getDestinationAirports(@RequestParam("originCityName") String originAirportIcao) {
        List<Airport> destinationAirports = connectionsService.findDestAirportByOrigAirport(airportService.findByIcao(originAirportIcao));
        return destinationAirports;
    }

    //for deletion if getDestinationAirport works
    @GetMapping("/getDestinationCity")
    public @ResponseBody List<String> getDestinationCity(@RequestParam("originCityName") String originCityName) {
        List<Airport> originAirports = connectionsService.findDestAirportByOrigAirport(airportService.findByCity(originCityName));
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
        List<Timestamp> flightTimes = connectionsService.findFlightDateByOrigAndDestAirport(origAirportObject, destAirportObject);
        List<String> result = new ArrayList<>();
        String dateFormat = "yyy-MM-dd";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);

        for (Timestamp tempTime:flightTimes) {
            result.add(formatter.format(tempTime.toLocalDateTime()));
        }
        return result;
    }

    @GetMapping("/confirm")
    public String confirm(@RequestParam("originAirportIcao") String originAirportIcao,
                          @RequestParam("destinationAirportIcao") String destinationAirportIcao,
                          @RequestParam("flightDate") String flightDate,
                          Model model) {

        Airport originAirportObject = airportService.findByIcao(originAirportIcao);
        Airport destinationAirportObject = airportService.findByIcao(destinationAirportIcao);
        List<Connections> connections = connectionsService.findFlightDateByOriginAirportAndDestinationAirportAndDepartureDay(originAirportObject, destinationAirportObject, flightDate);

        List<List<String>> flightTimes = new ArrayList<>();
        String dateFormat = "HH:mm";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);

        for (Connections tempConnections:connections) {
            flightTimes.add(new ArrayList<>(){
                {
                    add(formatter.format(tempConnections.getDepartureDate().toLocalDateTime()));
                    add(formatter.format(tempConnections.getArrivalDate().toLocalDateTime()));
                }
            });
        }

        model.addAttribute("originAirportCity", originAirportObject.getCity());
        model.addAttribute("destinationAirportCity", destinationAirportObject.getCity());
        model.addAttribute("flightDate", flightDate);
        model.addAttribute("flightTimes", flightTimes);
        return "confirm";
    }


}
