package it.epicode.capstonebe.services;

import it.epicode.capstonebe.exceptions.BadRequestException;
import it.epicode.capstonebe.exceptions.NotFoundException;
import it.epicode.capstonebe.models.entities.Destination;
import it.epicode.capstonebe.models.entities.Trip;
import it.epicode.capstonebe.models.requestDTO.TripDTO;
import it.epicode.capstonebe.repositories.DestinationRepository;
import it.epicode.capstonebe.repositories.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TripService {
    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private DestinationRepository destinationRepository;

    public Page<Trip> getAll(Pageable pageable) {

        return tripRepository.findAll(pageable);

    }

    public Trip getById(Long id) throws NotFoundException {

        return tripRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Trip with id " + id + " not found."));

    }

    public Page<Trip> getClosestDepartureTripsLimited(int pageSize) {
        Pageable pageable = PageRequest.of(0, pageSize);
        return tripRepository.findClosestDepartureTrips(pageable);
    }

    public Page<Trip> getClosestDepartureTrips(Pageable pageable) {
        return tripRepository.findClosestDepartureTrips(pageable);
    }

    public Trip save(TripDTO tripDTO) throws BadRequestException {

        Trip trip = new Trip();
        Destination destination = destinationRepository.getById(tripDTO.destinationId());

        trip.setDestination     (destination);
        trip.setDepartureDate   (tripDTO.departureDate());
        trip.setReturningDate   (tripDTO.returningDate());
        trip.setPrice           (tripDTO.price());
        trip.setMaxParticipants (tripDTO.maxParticipants());
        trip.setSpotsLeft       (trip.getMaxParticipants());
        destination.getTrips    ().add(trip);

        return tripRepository.save(trip);

    }

    public Trip update(Long id, TripDTO tripDTO) throws NotFoundException, BadRequestException {

        Trip trip = getById(id);

        if (tripDTO.departureDate() != null) {
            trip.setDepartureDate   (tripDTO.departureDate());
        }
        if (tripDTO.returningDate() != null) {
            trip.setReturningDate   (tripDTO.returningDate());
        }
        if (tripDTO.price() != null) {
            trip.setPrice           (tripDTO.price());
        }
        if (tripDTO.maxParticipants() != null) {
            trip.setMaxParticipants (tripDTO.maxParticipants());
        }
        if (tripDTO.destinationId() != null) {
            Destination destination = destinationRepository.getById(tripDTO.destinationId());
            trip.setDestination     (destination);
        }

        return tripRepository.save(trip);

    }

    public void delete(Long id) throws NotFoundException {

        Trip trip = getById(id);
        tripRepository.delete(trip);

    }
}

