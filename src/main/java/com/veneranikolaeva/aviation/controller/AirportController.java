package com.veneranikolaeva.aviation.controller;

import com.veneranikolaeva.aviation.entity.Flight;
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

    @GetMapping
    public List<Airport> getAllAirports() {
        return airportRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Airport> getAirportById(@PathVariable Long id) {
        return airportRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

        @PostMapping
    public ResponseEntity<Airport> createAirport(@Valid @RequestBody Airport airport) {
        Airport saved = airportRepository.save(airport);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Airport> updateAirport(@PathVariable Long id, @RequestBody Airport airportDetails) {
        return airportRepository.findById(id)
                .map(airport -> {
                    airport.setName(airportDetails.getName());
                    airport.setCode(airportDetails.getCode());
                    airport.setCity(airportDetails.getCity());
                    Airport updatedAirport = airportRepository.save(airport);
                    return ResponseEntity.ok(updatedAirport);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAirport(@PathVariable Long id) {
        return airportRepository.findById(id)
                .map(airport -> {
                    airportRepository.delete(airport);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/{id}/departures")
    public ResponseEntity<List<Flight>> getDepartures(@PathVariable Long id) {
        // Implement this method based on your FlightRepository
        List<Flight> flights = flightRepository.findByDepartureAirportId(id);
        return ResponseEntity.ok(flights);
    }

    // Relationship: Get all flights landing at this airport
    @GetMapping("/{id}/arrivals")
    public ResponseEntity<List<Flight>> getArrivals(@PathVariable Long id) {
        // Implement this method based on your FlightRepository
        List<Flight> flights = flightRepository.findByLandingAirportId(id);
        return ResponseEntity.ok(flights);
    }
}