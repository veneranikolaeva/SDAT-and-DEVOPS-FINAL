package com.veneranikolaeva.aviation.repository;

import com.veneranikolaeva.aviation.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
}
