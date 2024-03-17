package it.epicode.capstonebe.controllers;

import com.cloudinary.Cloudinary;
import it.epicode.capstonebe.exceptions.BadRequestException;
import it.epicode.capstonebe.exceptions.HandlerException;
import it.epicode.capstonebe.exceptions.InternalServerErrorException;
import it.epicode.capstonebe.exceptions.UnauthorizedException;
import it.epicode.capstonebe.models.entities.User;
import it.epicode.capstonebe.models.requestDTO.PasswordUpdateDTO;
import it.epicode.capstonebe.models.requestDTO.UserUpdateDTO;
import it.epicode.capstonebe.models.responseDTO.ConfirmRes;
import it.epicode.capstonebe.models.responseDTO.DeleteRes;
import it.epicode.capstonebe.security.JwtTools;
import it.epicode.capstonebe.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    JwtTools jwtTools;

    @Autowired
    UserService userService;

    /*  ------------------------------ USER SELF ACCOUNT MODIFICATION ------------------------------  */

    @GetMapping("")
    public User getProfile() throws UnauthorizedException, BadRequestException {
        UUID userId = jwtTools.extractUserIdFromReq();
        return userService.getById(userId);
    }

    @PatchMapping("/update-password")
    public ConfirmRes updatePassword(@Validated @RequestBody PasswordUpdateDTO passwordUpdateDTO, BindingResult validation) throws UnauthorizedException, BadRequestException {
        HandlerException.exception(validation);
        UUID userId = jwtTools.extractUserIdFromReq();
        User user = userService.getById(userId);
        userService.updatePassword(passwordUpdateDTO.oldPassword(), passwordUpdateDTO.newPassword(), user);
        return new ConfirmRes("The password has been successfully changed.", HttpStatus.OK);
    }

    @PatchMapping("/update")
    public User update(@Validated @RequestBody UserUpdateDTO userUpdateDTO, BindingResult validation) throws  UnauthorizedException, BadRequestException, InternalServerErrorException {
        HandlerException.exception(validation);
        UUID userId = jwtTools.extractUserIdFromReq();
        return userService.update(userId, userUpdateDTO);
    }

    @PatchMapping("/upload-pfp")
    public User upload(@RequestParam("file") MultipartFile file) throws IOException, UnauthorizedException, BadRequestException {
        UUID userId = jwtTools.extractUserIdFromReq();
        User user = userService.getById(userId);
        String url = (String) cloudinary.uploader().upload(file.getBytes(), new HashMap<>()).get("url");
        return userService.updatePfp(user, url);
    }

    @DeleteMapping("/delete")
    public DeleteRes delete() throws UnauthorizedException, BadRequestException {
        UUID userId = jwtTools.extractUserIdFromReq();
        User user = userService.getById(userId);
        String username = user.getUsername();
        userService.delete(userId);
        return new DeleteRes("User (username='" + username + "', id='" + userId + "') successfully deleted");
    }

    /*  ------------------------------ ADMIN ACCOUNT MODIFICATION ------------------------------  */



}
