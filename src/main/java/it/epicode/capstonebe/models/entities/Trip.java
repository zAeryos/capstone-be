package it.epicode.capstonebe.models.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "trips")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trip_sequence")
    @SequenceGenerator(name = "trip_sequence", initialValue = 1, allocationSize = 1)
    @Setter(AccessLevel.NONE)
    private Long            trip_id;
    private LocalDate       departureDate;
    private LocalDate       returningDate;
    private double          price;
    private int             maxPartecipants;

    @ManyToOne
    @JoinColumn(name = "destination_id", nullable = false)
    private Destination     destination;

    @OneToMany(mappedBy = "trip")
    private List<Booking>   bookingList;

    public boolean availablePlaces() {
        return bookingList.size() < maxPartecipants;
    }
}

