package it.epicode.capstonebe.services;

import it.epicode.capstonebe.exceptions.BadRequestException;
import it.epicode.capstonebe.exceptions.InternalServerErrorException;
import it.epicode.capstonebe.exceptions.UnauthorizedException;
import it.epicode.capstonebe.models.entities.User;
import it.epicode.capstonebe.models.requestDTO.UserDTO;
import it.epicode.capstonebe.models.responseDTO.AccessTokenRes;
import it.epicode.capstonebe.repositories.UserRepository;
import it.epicode.capstonebe.security.JwtTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private JwtTools jwtTools;

    public User register(UserDTO userDTO) throws BadRequestException, InternalServerErrorException {
        User u = new User(
                userDTO.name(),
                userDTO.surname(),
                userDTO.email(),
                userDTO.phoneNumber(),
                userDTO.birthday(),
                userDTO.username(),
                encoder.encode(userDTO.password())
                );
        try {
            return userRepo.save(u);
        } catch (DataIntegrityViolationException e) {
            if (userRepo.getAllEmails().contains(u.getEmail()))
                throw new BadRequestException("email già esistente, impossibile creare");
            if (userRepo.getAllUsernames().contains(u.getUsername()))
                throw new BadRequestException("username già esistente, impossibile creare");
            throw new InternalServerErrorException("Errore di violazione dell'* integrità dei dati: " + e.getMessage());

        }
    }

    public Optional<User> findByUserId(UUID userId) {
        return userRepo.findById(userId);
    }

    public AccessTokenRes login(String username, String password) throws UnauthorizedException {
        User u = userRepo.findByUsername(username).orElseThrow(
                () -> new UnauthorizedException("Email/password errati")
        );
        if (!encoder.matches(password, u.getPassword()))
            throw new UnauthorizedException("Email/password errati");
        return new AccessTokenRes(jwtTools.createToken(u));
    }

}
