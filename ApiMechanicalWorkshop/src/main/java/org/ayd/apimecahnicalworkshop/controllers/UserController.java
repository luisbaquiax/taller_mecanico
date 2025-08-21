package org.ayd.apimecahnicalworkshop.controllers;

import org.ayd.apimecahnicalworkshop.entities.User;
import org.ayd.apimecahnicalworkshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.ayd.apimecahnicalworkshop.utils.Encriptation;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    private Encriptation encriptation;

    public UserController() {
        this.encriptation = new Encriptation();
    }

    @PostMapping("/save")
    public User saveUser(@RequestBody User user) {
        user.setPassword(encriptation.encrypt(user.getPassword()));
        return userService.saveUser(user);
    }

    @GetMapping("/all")
    public Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
