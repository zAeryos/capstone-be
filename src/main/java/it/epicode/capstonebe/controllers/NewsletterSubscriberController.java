package it.epicode.capstonebe.controllers;

import it.epicode.capstonebe.exceptions.BadRequestException;
import it.epicode.capstonebe.exceptions.HandlerException;
import it.epicode.capstonebe.exceptions.InternalServerErrorException;
import it.epicode.capstonebe.exceptions.NotFoundException;
import it.epicode.capstonebe.models.entities.Booking;
import it.epicode.capstonebe.models.entities.NewsletterSubscriber;
import it.epicode.capstonebe.models.requestDTO.BookingDTO;
import it.epicode.capstonebe.models.requestDTO.NewsletterSubscriberDTO;
import it.epicode.capstonebe.services.NewsletterSubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/newsletter")
public class NewsletterSubscriberController {

    @Autowired
    private NewsletterSubscriberService newsletterService;

    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<NewsletterSubscriber> getAllSubscribers(Pageable pageable) {
        return newsletterService.getAllEmails(pageable);
    }

    @PostMapping("/save")
    public NewsletterSubscriber saveEmail(@RequestBody NewsletterSubscriberDTO newsletterDTO, BindingResult bindingResult) throws NotFoundException, BadRequestException, InternalServerErrorException {
        HandlerException.notFoundException(bindingResult);
        return newsletterService.saveEmail(newsletterDTO);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteEmail(@PathVariable Long id) throws NotFoundException {

            newsletterService.removeEmail(id);
            return "The email has been successfully removed from the newsletter.";

    }

}

/*

*     @PostMapping("/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Booking saveBooking(@RequestBody BookingDTO bookingDTO, BindingResult bindingResult) throws NotFoundException, BadRequestException, InternalServerErrorException {
        HandlerException.notFoundException(bindingResult);
        return bookingService.save(bookingDTO);
    }
    *
    *     @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteBooking(@PathVariable UUID id) throws NotFoundException {

        bookingService.delete(id);
        return "The booking has been successfully deleted";
    }
* */