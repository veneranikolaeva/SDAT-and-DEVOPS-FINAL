package com.veneranikolaeva.aviation.controller;

import com.veneranikolaeva.aviation.entity.Flight;
import com.veneranikolaeva.aviation.entity.Passenger;
import com.veneranikolaeva.aviation.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/passengers")
public class PassengerController {

    @Autowired
    private PassengerRepository passengerRepository;

    // Get all passengers
    @GetMapping
    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    // Get passenger by ID
    @GetMapping("/{id}")
    public ResponseEntity<Passenger> getPassengerById(@PathVariable Integer id) {
        return passengerRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new passenger
    @PostMapping
    public ResponseEntity<Passenger> createPassenger(@Valid @RequestBody Passenger passenger) {
        Passenger saved = passengerRepository.save(passenger);
        return ResponseEntity.ok(saved);
    }

    // Update existing passenger
    @PutMapping("/{id}")
    public ResponseEntity<Passenger> updatePassenger(@PathVariable Integer id, @Valid @RequestBody Passenger passengerDetails) {
        return passengerRepository.findById(id)
                .map(passenger -> {
                    passenger.setFirstName(passengerDetails.getFirstName());
                    passenger.setLastName(passengerDetails.getLastName());
                    passenger.setPhoneNumber(passengerDetails.getPhoneNumber());
                    passenger.setCity(passengerDetails.getCity());
                    passengerRepository.save(passenger);
                    return ResponseEntity.ok(passenger);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete passenger
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePassenger(@PathVariable Integer id) {
        return passengerRepository.findById(id)
                .map(passenger -> {
                    passengerRepository.delete(passenger);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Get all flights of a passenger
    @GetMapping("/{id}/flights")
    public ResponseEntity<List<Flight>> getFlightsForPassenger(@PathVariable Integer id) {
        return passengerRepository.findById(id).map(passenger -> {
            List<Flight> flights = new ArrayList<Flight>(passenger.getFlights());
            return ResponseEntity.ok(flights);
        }).orElse(ResponseEntity.notFound().build());
    }
}
