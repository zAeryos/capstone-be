package it.epicode.capstonebe.services;

import it.epicode.capstonebe.exceptions.BadRequestException;
import it.epicode.capstonebe.exceptions.InternalServerErrorException;
import it.epicode.capstonebe.exceptions.UnauthorizedException;
import it.epicode.capstonebe.models.entities.User;
import it.epicode.capstonebe.models.requestDTO.UserDTO;
import it.epicode.capstonebe.models.requestDTO.UserUpdateDTO;
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

    public User update(UUID id, UserUpdateDTO userDTO) throws BadRequestException, BadRequestException, InternalServerErrorException {
        User user = getById(id);

        if (userDTO.name() != null && !userDTO.name().isEmpty()) {
            user.setName        (userDTO.name());
        }
        if (userDTO.surname() != null && !userDTO.surname().isEmpty()) {
            user.setSurname     (userDTO.surname());
        }
        if (userDTO.email() != null && !userDTO.email().isEmpty()) {
            user.setEmail       (userDTO.email());
        }
        if (userDTO.phoneNumber() != null && !userDTO.phoneNumber().isEmpty()) {
            user.setPhoneNumber (userDTO.phoneNumber());
        }
        if (userDTO.username() != null && !userDTO.username().isEmpty()) {
            user.setUsername    (userDTO.username());
        }

        try {

            userRepo.save(user);
            mailService.sendEmail(
                    user.getEmail(),
                    "YOUR ACCOUNT DETAILS HAVE BEEN MODIFIED",
                    "Hello" + user.getUsername() + ", you have modified your account details with success!"
            );
            return(user);
        } catch (DataIntegrityViolationException e) {
            if (userRepo.getAllUsernames().contains(user.getUsername()) || userRepo.getAllEmails().contains(user.getEmail()) || userRepo.getAllPhoneNumbers().contains(user.getPhoneNumber()))
                throw new BadRequestException("The username, email or phone number you have chosen are already taken. Try again.");
            throw new InternalServerErrorException("Error with the data sent: " + e.getMessage());
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
