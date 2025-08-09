package repository;

import entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {
}