package org.ayd.apimecahnicalworkshop.controllers;

import org.ayd.apimecahnicalworkshop.dto.userdto.UserDTO;
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

    @PostMapping("/save")
    public UserResponseDTO saveUser(@RequestBody UserDTO userDTO) {
        return userService.saveUser(userDTO);
    }

    @GetMapping("/all")
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }
}
