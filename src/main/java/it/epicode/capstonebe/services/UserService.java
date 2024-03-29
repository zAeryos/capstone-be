package it.epicode.capstonebe.services;

import it.epicode.capstonebe.exceptions.BadRequestException;
import it.epicode.capstonebe.exceptions.InternalServerErrorException;
import it.epicode.capstonebe.exceptions.UnauthorizedException;
import it.epicode.capstonebe.models.entities.User;
import it.epicode.capstonebe.models.requestDTO.UserDTO;
import it.epicode.capstonebe.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserRepository  userRepo;
    @Autowired
    private MailService     mailService;

    public Page<User> getAll(Pageable pageable) {
        return userRepo.findAll(pageable);
    }

    public User getById(UUID id) throws BadRequestException {
        return userRepo.findById(id)
                .orElseThrow(() -> new BadRequestException("The user with ID " + id + "does not exist."));
    }

    public User save(UserDTO user) throws BadRequestException, InternalServerErrorException {
        User u = new User();
        u.setName(user.name());
        u.setSurname(user.surname());
        u.setPhoneNumber(user.phoneNumber());
        u.setEmail(user.email());
        u.setUsername(user.username());
        u.setPassword(encoder.encode(user.password()));
        try {
            userRepo.save(u);
            mailService.sendEmail(
                    u.getEmail(),
                    "You have been successfully registered.",
                    "Welcome " + u.getName() + ", we hope to see you in our trips soon!"
            );
        } catch (DataIntegrityViolationException e) {
            if (userRepo.getAllEmails().contains(u.getEmail()))
                throw new BadRequestException("The email you have chosen is already assigned to another account.");
            if (userRepo.getAllUsernames().contains(u.getUsername()))
                throw new BadRequestException("Username already exists, choose another username and try again.");
            if (userRepo.getAllPhoneNumbers().contains(u.getPhoneNumber()))
                throw new BadRequestException("The phone number you have chosen is already assigned to another account.");
            throw new InternalServerErrorException("Error with the data sent: " + e.getMessage());  //TODO specify better error message
        }
        return u;
    }

    public User update(UUID id, UserDTO user) throws BadRequestException, BadRequestException, InternalServerErrorException {
        User u = getById(id);

        u.setName(user.name());
        u.setSurname(user.surname());
        u.setEmail(user.email());
        u.setUsername(user.username());
        try {

            userRepo.save(u);
            mailService.sendEmail(
                    u.getEmail(),
                    "YOUR ACCOUNT DETAILS HAVE BEEN MODIFIED",
                    "Hello" + u.getUsername() + ", you have modified your account details with success!"
            );
            return(u);
        } catch (DataIntegrityViolationException e) {
            if (userRepo.getAllUsernames().contains(u.getUsername()) || userRepo.getAllEmails().contains(u.getEmail()) || userRepo.getAllPhoneNumbers().contains(u.getPhoneNumber()))
                throw new BadRequestException("Lo username e/o la password impostati esistono già. Impossibile aggiornare");
            throw new InternalServerErrorException("Errore di violazione dell' integrità dei dati: " + e.getMessage());
        }
    }

    public void updatePassword(String oldPassword, String newPassword, User user) throws UnauthorizedException {
        if (!encoder.matches(oldPassword, user.getPassword()))
            throw new UnauthorizedException("The old password is incorrect. Try again.");
        user.setPassword(encoder.encode(newPassword));
        userRepo.save(user);
    }

    public User updatePfp(User user, String url) {
        user.setAvatar(url);
        return userRepo.save(user);
    }

    public void delete(UUID id) throws BadRequestException {
        User u = getById(id);
        userRepo.delete(u);
    }
}
