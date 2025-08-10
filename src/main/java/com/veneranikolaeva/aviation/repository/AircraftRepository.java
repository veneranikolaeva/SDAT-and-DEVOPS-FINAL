package com.veneranikolaeva.aviation.repository;

import com.veneranikolaeva.aviation.entity.Aircraft;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
}