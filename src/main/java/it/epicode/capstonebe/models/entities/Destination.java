package it.epicode.capstonebe.models.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "destinations")
public class Destination {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "destination_sequence")
    @SequenceGenerator(name = "destination_sequence", initialValue = 1, allocationSize = 1)
    @Setter(AccessLevel.NONE)
    private     Long        destination_id;
    @Column(unique = true)
    private     String      name;
    private     String      description;
    private     String      image;
    @Column(name="avg_rating")
    private     double      avgRating;

    @OneToMany(mappedBy = "destination")
    private     List<Trip>  trips;
}

