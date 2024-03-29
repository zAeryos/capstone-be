package it.epicode.capstonebe.services;

import it.epicode.capstonebe.exceptions.NotFoundException;
import it.epicode.capstonebe.models.entities.Booking;
import it.epicode.capstonebe.models.entities.Trip;
import it.epicode.capstonebe.models.entities.User;
import it.epicode.capstonebe.models.enums.BookingStatus;
import it.epicode.capstonebe.models.requestDTO.BookingDTO;
import it.epicode.capstonebe.repositories.BookingRepository;
import it.epicode.capstonebe.repositories.TripRepository;
import it.epicode.capstonebe.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookingService {

    @Autowired
    private BookingRepository   bookingRepository;

    @Autowired
    private UserRepository      userRepository;
    @Autowired
    private TripRepository      tripRepository;

    public Page<Booking> getAll(Pageable pageable) {

        return bookingRepository.findAll(pageable);

    }

    public Booking getById(UUID bookingId) throws NotFoundException {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Booking with id " + bookingId + " not found."));
    }

    public Booking save(UUID userId, BookingDTO bookingDTO) {

        User    user    = userRepository.getById(userId);
        Trip    trip    = tripRepository.getById(bookingDTO.tripId());

        if (trip.getSpotsLeft() < bookingDTO.participantsNumber()) {
            throw new IllegalArgumentException("Not enough spots left for the booking.");
        }

        Booking booking = new Booking(bookingDTO.participantsNumber(), user, trip);


        booking.setTotalCost(trip.getPrice() * bookingDTO.participantsNumber());
        trip.setSpotsLeft(trip.getSpotsLeft() - bookingDTO.participantsNumber());

                tripRepository   .save(trip);
        return  bookingRepository.save(booking);

    }

    public Booking saveAdmin(BookingDTO bookingDTO) {

        User    user    = userRepository.getById(bookingDTO.userId());
        Trip    trip    = tripRepository.getById(bookingDTO.tripId());

        if (trip.getSpotsLeft() < bookingDTO.participantsNumber()) {
            throw new IllegalArgumentException("Not enough spots left for the booking.");
        }

        Booking booking = new Booking(bookingDTO.participantsNumber(), user, trip);


        booking.setTotalCost(trip.getPrice() * bookingDTO.participantsNumber());
        trip.setSpotsLeft(trip.getSpotsLeft() - bookingDTO.participantsNumber());

        tripRepository   .save(trip);
        return  bookingRepository.save(booking);

    }

    public Booking updateBookingStatus(UUID bookingId, BookingStatus newStatus) throws NotFoundException {
        Booking booking = getById(bookingId);
        booking.setBookingStatus(newStatus);
        return bookingRepository.save(booking);
    }

    public void delete(UUID bookingId) throws NotFoundException {
        Booking booking = getById(bookingId);
        bookingRepository.delete(booking);
    }

}

