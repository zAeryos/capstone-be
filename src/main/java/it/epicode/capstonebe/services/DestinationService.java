package it.epicode.capstonebe.services;

import it.epicode.capstonebe.exceptions.BadRequestException;
import it.epicode.capstonebe.exceptions.NotFoundException;
import it.epicode.capstonebe.models.entities.Destination;
import it.epicode.capstonebe.models.requestDTO.DestinationDTO;
import it.epicode.capstonebe.repositories.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DestinationService {

    @Autowired
    private DestinationRepository destinationRepository;

    public Page<Destination> getAll(Pageable pageable) {

        return destinationRepository.findAll(pageable);

    }

    public Destination getById(Long id) throws NotFoundException{

        return destinationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Destination with id " + id + " not found."));

    }

    public Destination save(DestinationDTO destinationDTO) throws BadRequestException {

        Destination destination = new Destination();

        destination.setName(destinationDTO.name());
        destination.setDescription(destinationDTO.description());

        return destinationRepository.save(destination);

    }

    public Destination update(Long id, DestinationDTO destinationDTO) throws NotFoundException, BadRequestException {

        Destination destination = getById(id);

        destination.setName(destinationDTO.name());
        destination.setDescription(destinationDTO.description());

        return destinationRepository.save(destination);

    }

    public void delete(Long id) throws NotFoundException {

        Destination destination = getById(id);

        destinationRepository.delete(destination);

    }
}

