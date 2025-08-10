package com.veneranikolaeva.aviation.repository;

import com.veneranikolaeva.aviation.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    // Find flights by departure airport ID
    List<Flight> findByDepartureAirportId(Long airportId);

    // Find flights by landing airport ID
    List<Flight> findByLandingAirportId(Long airportId);
}