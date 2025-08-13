package com.veneranikolaeva.aviation.controller;

import com.veneranikolaeva.aviation.entity.Airport;
import com.veneranikolaeva.aviation.repository.AirportRepository;
import com.veneranikolaeva.aviation.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/airports")
public class AirportController {

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private FlightRepository flightRepository;

    // Get all airports
    @GetMapping
    public List<Airport> getAllAirports() {
        return airportRepository.findAll();
    }

    // Get airport by ID
    @GetMapping("/{id}")
    public ResponseEntity<Airport> getAirportById(@PathVariable Integer id) {
        return airportRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    // Get airports by city
    @GetMapping("/city/{cityId}")
    public ResponseEntity<List<Airport>> getAirportsByCity(@PathVariable Integer cityId) {
        List<Airport> airports = airportRepository.findByCityId(cityId);
        return ResponseEntity.ok(airports);
    }


    // Create new airport
    @PostMapping
    public ResponseEntity<Airport> createAirport(@Valid @RequestBody Airport airport) {
        Airport saved = airportRepository.save(airport);
        return ResponseEntity.ok(saved);
    }

    // Update airport
    @PutMapping("/{id}")
    public ResponseEntity<Airport> updateAirport(@PathVariable Integer id, @Valid @RequestBody Airport airportDetails) {
        return airportRepository.findById(id)
                .map(airport -> {
                    airport.setName(airportDetails.getName());
                    airport.setCode(airportDetails.getCode());
                    airport.setCity(airportDetails.getCity());
                    airportRepository.save(airport);
                    return ResponseEntity.ok(airport);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete airport
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAirport(@PathVariable Integer id) {
        return airportRepository.findById(id)
                .map(airport -> {
                    airportRepository.delete(airport);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Get flights departing from this airport
    @GetMapping("/{id}/departures")
    public ResponseEntity<List<com.veneranikolaeva.aviation.entity.Flight>> getDepartures(@PathVariable Integer id) {
        List<com.veneranikolaeva.aviation.entity.Flight> flights = flightRepository.findByDepartureAirportId(id);
        return ResponseEntity.ok(flights);
    }

    // Get flights arriving at this airport
    @GetMapping("/{id}/arrivals")
    public ResponseEntity<List<com.veneranikolaeva.aviation.entity.Flight>> getArrivals(@PathVariable Integer id) {
        List<com.veneranikolaeva.aviation.entity.Flight> flights = flightRepository.findByLandingAirportId(id);
        return ResponseEntity.ok(flights);
    }
}