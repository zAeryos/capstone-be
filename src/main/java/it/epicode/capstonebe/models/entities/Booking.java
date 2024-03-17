package it.epicode.capstonebe.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.epicode.capstonebe.models.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "bookings")
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private     UUID                booking_id;
    @Setter(AccessLevel.NONE)
    private     LocalDateTime       bookingTime;
    private     int                 participantsNumber;
    private     double              totalCost;
    @Enumerated(EnumType.STRING)
    private     BookingStatus       bookingStatus;
    // private     List<Participant>   participants = new ArrayList();  TODO implement participants if necessary.

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private     User                user;

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    @JsonIgnore
    private     Trip                trip;

    public Booking(int participantsNumber, User user, Trip trip) {

        this.participantsNumber = participantsNumber;
        this.user               = user;
        this.trip               = trip;
        this.bookingTime        = LocalDateTime.now();
        this.bookingStatus      = BookingStatus.PENDING;

    }
}

