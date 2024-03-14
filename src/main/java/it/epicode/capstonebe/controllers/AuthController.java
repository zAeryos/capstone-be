package it.epicode.capstonebe.controllers;

import it.epicode.capstonebe.exceptions.BadRequestException;
import it.epicode.capstonebe.exceptions.HandlerException;
import it.epicode.capstonebe.exceptions.InternalServerErrorException;
import it.epicode.capstonebe.exceptions.UnauthorizedException;
import it.epicode.capstonebe.models.entities.User;
import it.epicode.capstonebe.models.requestDTO.LoginDTO;
import it.epicode.capstonebe.models.requestDTO.UserDTO;
import it.epicode.capstonebe.models.responseDTO.AccessTokenRes;
import it.epicode.capstonebe.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authSvc;


    @PostMapping("/register")
    public User register(@Validated @RequestBody UserDTO userDTO, BindingResult validation) throws BadRequestException, InternalServerErrorException {

        HandlerException.exception(validation);
        return authSvc.register(userDTO);

    }

    @PostMapping("/login")
    public AccessTokenRes login(@Validated @RequestBody LoginDTO loginDTO, BindingResult validation) throws BadRequestException, UnauthorizedException {

        HandlerException.exception(validation);
        return authSvc.login(loginDTO.username(), loginDTO.password());

    }

}
