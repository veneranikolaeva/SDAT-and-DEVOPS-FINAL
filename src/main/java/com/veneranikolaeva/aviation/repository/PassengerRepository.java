package com.veneranikolaeva.aviation.repository;

import com.veneranikolaeva.aviation.entity.Passenger;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {
}