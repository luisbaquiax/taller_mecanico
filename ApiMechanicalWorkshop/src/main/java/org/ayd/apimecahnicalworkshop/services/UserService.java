package org.ayd.apimecahnicalworkshop.services;

import org.ayd.apimecahnicalworkshop.dto.userdto.UserDTO;
import org.ayd.apimecahnicalworkshop.dto.userdto.UserResponseDTO;
import org.ayd.apimecahnicalworkshop.entities.User;
import org.ayd.apimecahnicalworkshop.repositories.UserRepository;
import org.ayd.apimecahnicalworkshop.utils.Encriptation;
import org.ayd.apimecahnicalworkshop.utils.ErrorApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.stream.Collectors;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    private Encriptation encriptation;
    public UserService() {
        this.encriptation = new Encriptation();
    }

    public UserResponseDTO saveUser(UserDTO user) {
        if (repository.existsByUsername(user.getUsername())) {
            throw new ErrorApi(409, "El nombre de usuario ya existe");
        }
        if (repository.findByEmail(user.getEmail()) != null) {
            throw new ErrorApi(409, "El correo electrónico ya existe");
        }
        User saveUser = new User();
        saveUser.setRolId(user.getRolId());
        saveUser.setUsername(user.getUsername());
        saveUser.setPassword(encriptation.encrypt(user.getPassword()));
        saveUser.setActive(true);
        saveUser.setEmail(user.getEmail());
        saveUser.setPhone(user.getPhone());
        saveUser.setName(user.getName());
        saveUser.setLastName(user.getLastName());
        saveUser.setTwoFactorAuth(false);
        saveUser.setTypeTwoFactorId(1L);
        saveUser.setCreatedAt(LocalDate.now().toString());
        saveUser.setUpdatedAt(LocalDate.now().toString());
        repository.save(saveUser);//save user to the database
        return  new UserResponseDTO(
                saveUser.getUserId(),
                saveUser.getRolId(),
                saveUser.getUsername(),
                saveUser.isActive(),
                saveUser.getEmail(),
                saveUser.getPhone(),
                saveUser.getName(),
                saveUser.getLastName(),
                saveUser.isTwoFactorAuth(),
                saveUser.getTypeTwoFactorId()
        );
    }

    public List<UserResponseDTO> getAllUsers() {
        List<User> users = ((List<User>) repository.findAll());
        return users.stream().map(user -> new UserResponseDTO(
                user.getUserId(),
                user.getRolId(),
                user.getUsername(),
                user.isActive(),
                user.getEmail(),
                user.getPhone(),
                user.getName(),
                user.getLastName(),
                user.isTwoFactorAuth(),
                user.getTypeTwoFactorId()
        )).collect(Collectors.toList());
    }

    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    public User findByUsername(String username) {
        return repository.findByUsername(username);
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
