package com.example.airlineticketservice.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Table(name="airport")
public class Airport {
    @Id
    @Column(name = "ICAO")
    private String icao;
    @Column(name = "country")
    private String country;
    @Column(name = "city")
    private String city;

    @OneToMany(mappedBy = "originAirport", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    List<Connections> connectionsOrigin;

    @OneToMany(mappedBy = "destinationAirport", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    List<Connections> connectionsDestination;

    public Airport() {
    }

    public Airport(String icao, String country, String city) {
        this.icao = icao;
        this.country = country;
        this.city = city;
    }

    public String getIcao() {
        return icao;
    }

    public void setIcao(String icao) {
        this.icao = icao;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Airport{" +
                "icao='" + icao + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}

