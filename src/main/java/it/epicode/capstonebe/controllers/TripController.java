package it.epicode.capstonebe.controllers;

import com.cloudinary.Cloudinary;
import it.epicode.capstonebe.exceptions.*;
import it.epicode.capstonebe.models.entities.Trip;
import it.epicode.capstonebe.models.requestDTO.TripDTO;
import it.epicode.capstonebe.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    @Autowired
    private TripService tripService;
    @Autowired
    private Cloudinary  cloudinary;

    @GetMapping("/getAll")
    public Page<Trip> getAllTrips(Pageable pageable) {
        return tripService.getAll(pageable);
    }

    @GetMapping("/id")
    public Trip getById(@RequestParam Long id) throws NotFoundException {
        return tripService.getById(id);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Trip saveTrip(@RequestBody TripDTO tripDTO, BindingResult bindingResult) throws NotFoundException, BadRequestException, InternalServerErrorException {
        HandlerException.notFoundException(bindingResult);
        return tripService.save(tripDTO);
    }

    @PatchMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Trip updateTrip(@PathVariable Long id, @RequestBody TripDTO tripDTO, BindingResult bindingResult) throws NotFoundException, BadRequestException, InternalServerErrorException {
        HandlerException.notFoundException(bindingResult);
        return tripService.update(id, tripDTO);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteTrip(@PathVariable Long id) throws NotFoundException {

        tripService.delete(id);
        return "The trip has been successfully deleted";
    }

}

