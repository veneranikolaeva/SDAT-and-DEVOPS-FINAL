package com.veneranikolaeva.aviation.controller;

import com.veneranikolaeva.aviation.entity.Aircraft;
import com.veneranikolaeva.aviation.entity.Airport;
import com.veneranikolaeva.aviation.repository.AircraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/aircrafts")
public class AircraftController {

    @Autowired
    private AircraftRepository aircraftRepository;

    @GetMapping
    public List<Aircraft> getAllAircrafts() {
        return aircraftRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aircraft> getAircraftById(@PathVariable Integer id) {
        return aircraftRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Aircraft createAircraft(@Valid @RequestBody Aircraft aircraft) {
        return aircraftRepository.save(aircraft);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aircraft> updateAircraft(@PathVariable Integer id, @Valid @RequestBody Aircraft aircraftDetails) {
        return aircraftRepository.findById(id)
                .map(aircraft -> {
                    aircraft.setType(aircraftDetails.getType());
                    aircraft.setAirlineName(aircraftDetails.getAirlineName());
                    aircraft.setNumberOfPassengers(aircraftDetails.getNumberOfPassengers());
                    Aircraft updatedAircraft = aircraftRepository.save(aircraft);
                    return ResponseEntity.ok(updatedAircraft);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/{id}/airports")
    public ResponseEntity<List<Airport>> getAirportsForAircraft(@PathVariable Integer id) {
        // Verify the aircraft exists
        Optional<Aircraft> aircraftOpt = aircraftRepository.findById(id);
        if (!aircraftOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        // Get the aircraft entity
        Aircraft aircraft = aircraftOpt.get();

        // Fetch associated airports
        Set<Airport> airports = aircraft.getAirports();

        return ResponseEntity.ok(new ArrayList<>(airports));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAircraft(@PathVariable Integer id) {
        return aircraftRepository.findById(id)
                .map(aircraft -> {
                    aircraftRepository.delete(aircraft);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
