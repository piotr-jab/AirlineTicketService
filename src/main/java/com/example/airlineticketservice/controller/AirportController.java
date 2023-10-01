package com.example.airlineticketservice.controller;

import com.example.airlineticketservice.entity.Airport;
import com.example.airlineticketservice.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/airports")
public class AirportController {
    private final AirportService airportService;

    @Autowired
    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @GetMapping("/list")
    public String listAirports(Model model) {
        List<Airport> airportList = airportService.findAll();

        model.addAttribute("airports", airportList);

        return "list-airports";
    }

}
