package com.veneranikolaeva.aviation.repository;

import com.veneranikolaeva.aviation.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer> {

    // Find flights by departure airport ID
    List<Flight> findByDepartureAirportId(Integer airportId);

    // Find flights by landing airport ID
    List<Flight> findByLandingAirportId(Integer airportId);
}