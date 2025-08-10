package controller;

import entity.Aircraft;
import repository.AircraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

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
    public ResponseEntity<Aircraft> getAircraftById(@PathVariable Long id) {
        return aircraftRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Aircraft createAircraft(@Valid @RequestBody Aircraft aircraft) {
        return aircraftRepository.save(aircraft);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aircraft> updateAircraft(@PathVariable Long id, @Valid @RequestBody Aircraft aircraftDetails) {
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAircraft(@PathVariable Long id) {
        return aircraftRepository.findById(id)
                .map(aircraft -> {
                    aircraftRepository.delete(aircraft);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
