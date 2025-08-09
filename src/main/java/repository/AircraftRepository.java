package repository;

import entity.Aircraft;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
}