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

    @GetMapping("/getAll")
    public Page<Destination> getAllDestinations(Pageable pageable) {
        return destinationService.getAll(pageable);
    }

    @GetMapping("/id")
    public Destination getById(@RequestParam Long id) throws NotFoundException {
        return destinationService.getById(id);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Destination saveDestination(@RequestBody DestinationDTO destinationDTO, BindingResult bindingResult) throws NotFoundException, BadRequestException, InternalServerErrorException {
        HandlerException.notFoundException(bindingResult);
        return destinationService.save(destinationDTO);
    }

    @PatchMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Destination updateDestination(@PathVariable Long id, @RequestBody DestinationDTO destinationDTO, BindingResult bindingResult) throws NotFoundException, BadRequestException, InternalServerErrorException {
        HandlerException.notFoundException(bindingResult);
        return destinationService.update(id, destinationDTO);
    }

    @PatchMapping("/{id}/upload")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Destination upload(@RequestParam("file") MultipartFile file, @PathVariable Long id) throws IOException, UnauthorizedException, NotFoundException {

        Destination destination = destinationService.getById(id);
        String      url         = (String) cloudinary.uploader().upload(file.getBytes(), new HashMap<>()).get("url");
        return destinationService.uploadImage(destination, url);

    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteDestination(@PathVariable Long id) throws NotFoundException {

        destinationService.delete(id);
        return "The destination has been successfully deleted";
    }

}

