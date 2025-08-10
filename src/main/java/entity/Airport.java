package entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "airports")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String code;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @OneToMany(mappedBy = "landingAirport")
    private Set<Flight> arrivals;

    @OneToMany(mappedBy = "departureAirport")
    private Set<Flight> departures;

    @ManyToMany(mappedBy = "airports")
    private Set<Aircraft> aircrafts;
}