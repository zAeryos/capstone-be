package it.epicode.capstonebe.controllers;

import com.cloudinary.Cloudinary;
import it.epicode.capstonebe.exceptions.*;
import it.epicode.capstonebe.models.entities.Destination;
import it.epicode.capstonebe.models.requestDTO.DestinationDTO;
import it.epicode.capstonebe.services.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/api/destinations")
public class DestinationController {

    @Autowired
    private DestinationService  destinationService;

    @Autowired
    private Cloudinary          cloudinary;

    @GetMapping("")
    public Page<Destination> getAllDestinations(Pageable pageable) {
        return destinationService.getAll(pageable);
    }

    @GetMapping("/id")
    public Destination getById(@RequestParam Long id) throws NotFoundException {
        return destinationService.getById(id);
    }

    @PatchMapping("/{id}/upload")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Destination upload(@RequestParam("file") MultipartFile file, @PathVariable Long id) throws IOException, UnauthorizedException, NotFoundException {

        Destination destination = destinationService.getById(id);
        String      url         = (String) cloudinary.uploader().upload(file.getBytes(), new HashMap<>()).get("url");
        return destinationService.uploadImage(destination, url);

    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Destination saveDestination(@RequestBody DestinationDTO destinationDTO, BindingResult bindingResult) throws NotFoundException, BadRequestException, InternalServerErrorException {
        HandlerException.notFoundException(bindingResult);
        return destinationService.save(destinationDTO);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Destination updateDestination(@PathVariable Long id, @RequestBody DestinationDTO destinationDTO, BindingResult bindingResult) throws NotFoundException, BadRequestException, InternalServerErrorException {
        HandlerException.notFoundException(bindingResult);
        return destinationService.update(id, destinationDTO);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteDestination(@PathVariable Long id) throws NotFoundException {
        destinationService.delete(id);
    }

}

