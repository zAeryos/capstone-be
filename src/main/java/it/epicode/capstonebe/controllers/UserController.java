package it.epicode.capstonebe.controllers;

import com.cloudinary.Cloudinary;
import it.epicode.capstonebe.exceptions.*;
import it.epicode.capstonebe.models.entities.User;
import it.epicode.capstonebe.models.requestDTO.PasswordUpdateDTO;
import it.epicode.capstonebe.models.requestDTO.UserDTO;
import it.epicode.capstonebe.models.requestDTO.UserUpdateDTO;
import it.epicode.capstonebe.models.responseDTO.ConfirmRes;
import it.epicode.capstonebe.models.responseDTO.DeleteRes;
import it.epicode.capstonebe.security.JwtTools;
import it.epicode.capstonebe.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
        return new DeleteRes("User: '" + username + "', id: '" + userId + "' successfully deleted");
    }

    /*  ------------------------------ ADMIN ACCOUNT MODIFICATION ------------------------------  */

    @GetMapping("/admin/getAll")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<User> getAllUsers(Pageable pageable) {
        return userService.getAll(pageable);
    }

    @GetMapping("/admin/id")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User getById(@RequestParam UUID id) throws BadRequestException {
        return userService.getById(id);
    }

    @PostMapping("/admin/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User saveUser(@RequestBody UserDTO userDTO, BindingResult bindingResult) throws NotFoundException, BadRequestException, InternalServerErrorException {
        HandlerException.notFoundException(bindingResult);
        return userService.save(userDTO);
    }

    @PatchMapping("/admin/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User updateTrip(@PathVariable UUID id, @RequestBody UserUpdateDTO userDTO, BindingResult bindingResult) throws NotFoundException, BadRequestException, InternalServerErrorException {
        HandlerException.notFoundException(bindingResult);
        return userService.update(id, userDTO);
    }

    @DeleteMapping("/admin/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public DeleteRes deleteTrip(@PathVariable UUID id) throws NotFoundException, BadRequestException{

        userService.delete(id);
        return new DeleteRes("The user with id '" + id + "' has been successfully deleted");

    }
}
