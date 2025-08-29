package org.ayd.apimecahnicalworkshop.controllers;

import jakarta.validation.Valid;
import org.ayd.apimecahnicalworkshop.dto.userdto.RegisterClientDTO;
import org.ayd.apimecahnicalworkshop.dto.userdto.UserDTO;
import org.ayd.apimecahnicalworkshop.dto.userdto.UserLoginDTO;
import org.ayd.apimecahnicalworkshop.dto.userdto.UserResponseDTO;
import org.ayd.apimecahnicalworkshop.entities.User;
import org.ayd.apimecahnicalworkshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.ayd.apimecahnicalworkshop.utils.Encriptation;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/createUser/{typeSupplier}")
    public UserResponseDTO saveUser(@Valid @RequestBody UserDTO userDTO, @PathVariable String typeSupplier) {
        return userService.saveUser(userDTO, typeSupplier);
    }

    @PutMapping("/updateUser")
    public UserResponseDTO updateUser(@Valid @RequestBody UserResponseDTO user) {
        return userService.updateUser(user);
    }

    @PostMapping("/createClient")
    public UserResponseDTO createClient(@Valid @RequestBody RegisterClientDTO client) {
        return userService.registerClient(client);
    }

    @GetMapping("/all")
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/isActive/{active}")
    public List<UserResponseDTO> getUsersByStatus(@PathVariable boolean active) {
        return userService.getUsersByActive(active);
    }

    @PostMapping("/login")
    public UserResponseDTO loginUser(@Valid @RequestBody UserLoginDTO userLogin) {
        return userService.userLogin(userLogin);
    }
}
