package it.epicode.capstonebe.models.entities;

import it.epicode.capstonebe.models.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private     UUID            booking_id;
    private     LocalDateTime   bookingTime;
    private     int             partecipantsNumber;
    @Enumerated(EnumType.STRING)
    private     BookingStatus   bookingStatus;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private     User            user;
    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private     Trip            trip;



}

