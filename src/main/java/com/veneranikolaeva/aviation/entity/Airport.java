package com.veneranikolaeva.aviation.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Set;

@Entity
@Table(name = "airports")
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String code;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany(mappedBy = "departureAirport")
    @JsonIgnore
    private Set<Flight> departures;

    @OneToMany(mappedBy = "landingAirport")
    @JsonIgnore
    private Set<Flight> arrivals;

    // ManyToMany with Aircraft
    @ManyToMany
    @JoinTable(
            name = "aircraft_airport",
            joinColumns = @JoinColumn(name = "airport_id"),
            inverseJoinColumns = @JoinColumn(name = "aircraft_id")
    )
    @JsonIgnore
    private Set<Aircraft> aircrafts;

    //Getters and setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Set<Flight> getDepartures() {
        return departures;
    }

    public void setDepartures(Set<Flight> departures) {
        this.departures = departures;
    }

    public Set<Flight> getArrivals() {
        return arrivals;
    }

    public void setArrivals(Set<Flight> arrivals) {
        this.arrivals = arrivals;
    }

    public Set<Aircraft> getAircrafts() {
        return aircrafts;
    }

    public void setAircrafts(Set<Aircraft> aircrafts) {
        this.aircrafts = aircrafts;
    }
}