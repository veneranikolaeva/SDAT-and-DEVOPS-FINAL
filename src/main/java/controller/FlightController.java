package controller;

import entity.Flight;
import repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    @Autowired
    private FlightRepository flightRepository;

    // Get all flights
    @GetMapping
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    // Get flight by ID
    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
        return flightRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a flight
    @PostMapping
    public ResponseEntity<Flight> createFlight(@Valid @RequestBody Flight flight) {
        Flight saved = flightRepository.save(flight);
        return ResponseEntity.ok(saved);
    }

    // Update a flight
    @PutMapping("/{id}")
    public ResponseEntity<Flight> updateFlight(@PathVariable Long id, @Valid @RequestBody Flight flightDetails) {
        return flightRepository.findById(id)
                .map(flight -> {
                    flight.setFlightNumber(flightDetails.getFlightNumber());
                    flight.setDepartureTime(flightDetails.getDepartureTime());
                    flight.setArrivalTime(flightDetails.getArrivalTime());
                    flight.setDepartureAirport(flightDetails.getDepartureAirport());
                    flight.setLandingAirport(flightDetails.getLandingAirport());
                    flight.setAircraft(flightDetails.getAircraft());
                    flight.setStatus(flightDetails.getStatus());
                    flightRepository.save(flight);
                    return ResponseEntity.ok(flight);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a flight
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFlight(@PathVariable Long id) {
        return flightRepository.findById(id)
                .map(flight -> {
                    flightRepository.delete(flight);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Get all passengers booked on this flight
    @GetMapping("/{id}/passengers")
    public ResponseEntity<List<entity.Passenger>> getPassengersOnFlight(@PathVariable Long id) {
        Optional<Flight> flightOpt = flightRepository.findById(id);
        if (!flightOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        // Assuming your Flight entity has `Set<Passenger> passengers;`
        return ResponseEntity.ok(new ArrayList<>(flightOpt.get().getPassengers()));
    }

    // Optional: get flights by departure airport
    @GetMapping("/departure/{airportId}")
    public List<Flight> getFlightsByDeparture(@PathVariable Long airportId) {
        return flightRepository.findByDepartureAirportId(airportId);
    }

    // Optional: get flights by landing airport
    @GetMapping("/landing/{airportId}")
    public List<Flight> getFlightsByLanding(@PathVariable Long airportId) {
        return flightRepository.findByLandingAirportId(airportId);
    }
}