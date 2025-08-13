package com.veneranikolaeva.aviation.repository;

import com.veneranikolaeva.aviation.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Integer> {
    List<Airport> findByCityId(Integer cityId);

}