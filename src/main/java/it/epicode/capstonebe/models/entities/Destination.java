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
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private     UUID        destination_id;
    @Column(unique = true)
    private     String      name;
    private     String      description;
    private     String      image;
    private     double      avgRating;

    @OneToMany(mappedBy = "destination")
    private     List<Trip>  trips;
}

//TODO Priority 1