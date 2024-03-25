package it.epicode.capstonebe.repositories;

import it.epicode.capstonebe.models.entities.Trip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long>, PagingAndSortingRepository<Trip, Long> {

    @Query("SELECT t FROM Trip t WHERE t.departureDate >= CURRENT_DATE ORDER BY t.departureDate ASC")
    Page<Trip> findClosestDepartureTrips(Pageable pageable);

}

