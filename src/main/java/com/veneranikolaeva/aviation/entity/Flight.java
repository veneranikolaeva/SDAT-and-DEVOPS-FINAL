package com.veneranikolaeva.aviation.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "flights")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String flightNumber;

    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String status;

    @ManyToOne
    @JoinColumn(name = "departure_airport_id")
    private Airport departureAirport;

    @ManyToOne
    @JoinColumn(name = "landing_airport_id")
    private Airport landingAirport;

    @ManyToOne
    @JoinColumn(name = "aircraft_id")
    private Aircraft aircraft;

    @ManyToMany(mappedBy = "flights")
    private Set<Passenger> passengers;

    // Getters and setters

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }
    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }
    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public Airport getDepartureAirport() {
        return departureAirport;
    }
    public void setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
    }

    public Airport getLandingAirport() {
        return landingAirport;
    }
    public void setLandingAirport(Airport landingAirport) {
        this.landingAirport = landingAirport;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }
    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public Set<Passenger> getPassengers() {
        return passengers;
    }
    public void setPassengers(Set<Passenger> passengers) {
        this.passengers = passengers;
    }
}