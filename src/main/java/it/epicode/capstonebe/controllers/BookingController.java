package it.epicode.capstonebe.controllers;

import it.epicode.capstonebe.exceptions.BadRequestException;
import it.epicode.capstonebe.exceptions.HandlerException;
import it.epicode.capstonebe.exceptions.InternalServerErrorException;
import it.epicode.capstonebe.exceptions.NotFoundException;
import it.epicode.capstonebe.models.entities.Booking;
import it.epicode.capstonebe.models.entities.Trip;
import it.epicode.capstonebe.models.enums.BookingStatus;
import it.epicode.capstonebe.models.requestDTO.BookingDTO;
import it.epicode.capstonebe.models.requestDTO.TripDTO;
import it.epicode.capstonebe.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/getAll")
    public Page<Booking> getAllBookings(Pageable pageable) {
        return bookingService.getAll(pageable);
    }

    @GetMapping("/id")
    public Booking getById(@RequestParam UUID id) throws NotFoundException {
        return bookingService.getById(id);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Booking saveBooking(@RequestBody BookingDTO bookingDTO, BindingResult bindingResult) throws NotFoundException, BadRequestException, InternalServerErrorException {
        HandlerException.notFoundException(bindingResult);
        return bookingService.save(bookingDTO);
    }

    @PatchMapping("/updateStatus/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Booking updateTrip(@PathVariable UUID id, @RequestBody String bookingStatus, BindingResult bindingResult) throws NotFoundException, BadRequestException, InternalServerErrorException {
        HandlerException.notFoundException(bindingResult);
                        bookingStatus   = bookingStatus.toUpperCase();
        BookingStatus   status          = BookingStatus.valueOf(bookingStatus);         //TODO Fix this method, not working
        return bookingService.updateBookingStatus(id, status);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteBooking(@PathVariable UUID id) throws NotFoundException {

        bookingService.delete(id);
        return "The booking has been successfully deleted";
    }

}

