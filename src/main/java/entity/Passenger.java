package entity;

import jakarta.persistence.*;
import lombok.*;

import jakarta.validation.constraints.*;
import java.util.Set;

@Entity
@Table(name = "passengers")
@Data  // Lombok: auto-generates getters/setters
@NoArgsConstructor
@AllArgsConstructor
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 50)
    private String firstName;

    @NotNull
    @Size(min = 2, max = 50)
    private String lastName;

    @NotNull
    @Pattern(regexp = "^\\+?[0-9\\- ]{7,15}$", message = "Invalid phone number")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToMany
    @JoinTable(
            name = "passenger_flights",
            joinColumns = @JoinColumn(name = "passenger_id"),
            inverseJoinColumns = @JoinColumn(name = "flight_id")
    )
    private Set<Flight> flights; // Relationship to Flight
}