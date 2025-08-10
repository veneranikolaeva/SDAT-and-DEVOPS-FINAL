package com.veneranikolaeva.aviation.repository;

import com.veneranikolaeva.aviation.entity.Airport;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {
}