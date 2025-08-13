package com.veneranikolaeva.aviation.controller;

import com.veneranikolaeva.aviation.entity.Airport;
import com.veneranikolaeva.aviation.entity.City;
import com.veneranikolaeva.aviation.repository.AirportRepository;
import com.veneranikolaeva.aviation.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private AirportRepository airportRepository;

    @GetMapping
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<City> getCityById(@PathVariable Integer id) {
        return cityRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public City createCity(@RequestBody City city) {
        return cityRepository.save(city);
    }

    @PutMapping("/{id}")
    public ResponseEntity<City> updateCity(@PathVariable Integer id, @RequestBody City cityDetails) {
        return cityRepository.findById(id)
                .map(city -> {
                    city.setName(cityDetails.getName());
                    city.setState(cityDetails.getState());
                    city.setPopulation(cityDetails.getPopulation());
                    City updatedCity = cityRepository.save(city);
                    return ResponseEntity.ok(updatedCity);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCity(@PathVariable Integer id) {
        return cityRepository.findById(id)
                .map(city -> {
                    cityRepository.delete(city);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/{id}/airports")
    public ResponseEntity<List<Airport>> getAirportsByCity(@PathVariable Integer id) {
        List<Airport> airports = airportRepository.findByCityId(id);
        return ResponseEntity.ok(airports);
    }
}
