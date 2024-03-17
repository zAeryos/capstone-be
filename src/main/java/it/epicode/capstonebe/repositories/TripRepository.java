package it.epicode.capstonebe.repositories;

import it.epicode.capstonebe.models.entities.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TripRepository extends JpaRepository<Trip, Long>, PagingAndSortingRepository<Trip, Long> {
}

