package it.epicode.capstonebe.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.ArrayList;
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
    private     String      longDescription;
    private     String      image;
    @Column(name="avg_rating")
    private     double      avgRating;

    @JsonIgnore
    @OneToMany(mappedBy = "destination")
    private     List<Trip>  trips = new ArrayList<>();
}

