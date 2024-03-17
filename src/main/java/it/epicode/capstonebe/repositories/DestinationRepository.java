package it.epicode.capstonebe.repositories;

import it.epicode.capstonebe.models.entities.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DestinationRepository extends JpaRepository<Destination, Long>, PagingAndSortingRepository<Destination, Long> {

}

