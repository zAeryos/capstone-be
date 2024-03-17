package it.epicode.capstonebe.repositories;

import it.epicode.capstonebe.models.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID>, PagingAndSortingRepository<Booking, UUID> {
}

