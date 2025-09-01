package org.ayd.apimecahnicalworkshop.controllers;

import jakarta.validation.Valid;
import org.ayd.apimecahnicalworkshop.dto.userdto.RegisterClientDTO;
import org.ayd.apimecahnicalworkshop.dto.userdto.UserDTO;
import org.ayd.apimecahnicalworkshop.dto.userdto.UserLoginDTO;
import org.ayd.apimecahnicalworkshop.dto.userdto.UserResponseDTO;
import org.ayd.apimecahnicalworkshop.entities.User;
import org.ayd.apimecahnicalworkshop.repositories.UserRepository;
import org.ayd.apimecahnicalworkshop.services.UserService;
import org.ayd.apimecahnicalworkshop.utils.ErrorApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ayd.apimecahnicalworkshop.utils.Encriptation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

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

    /**
     * Obtener usuario por ID
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long userId) {
        try {
            var user = userRepository.findById(userId);
            if (user.isEmpty()) {
                throw new ErrorApi(404, "Usuario no encontrado");
            }

            User foundUser = user.get();
            UserResponseDTO response = new UserResponseDTO(
                    foundUser.getUserId(),
                    foundUser.getRolId(),
                    foundUser.getUsername(),
                    foundUser.isActive(),
                    foundUser.getEmail(),
                    foundUser.getPhone(),
                    foundUser.getName(),
                    foundUser.getLastName(),
                    foundUser.isTwoFactorAuth(),
                    foundUser.getTypeTwoFactorId()
            );

            return ResponseEntity.ok(response);
        } catch (ErrorApi e) {
            throw e;
        } catch (Exception e) {
            throw new ErrorApi(500, "Error interno del servidor");
        }
    }

    /**
     * Cambiar contraseña
     */
    @PutMapping("/changePassword")
    public ResponseEntity<Map<String, Object>> changePassword(@Valid @RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            Long userId = Long.valueOf(request.get("userId").toString());
            String currentPassword = (String) request.get("currentPassword");
            String newPassword = (String) request.get("newPassword");

            // Buscar usuario
            var userOpt = userRepository.findById(userId);
            if (userOpt.isEmpty()) {
                throw new ErrorApi(404, "Usuario no encontrado");
            }
            User user = userOpt.get();

            Encriptation encriptation = new Encriptation();
            if (!encriptation.matches(currentPassword, user.getPassword())) {
                throw new ErrorApi(401, "Contraseña actual incorrecta");
            }
            user.setPassword(encriptation.encrypt(newPassword));
            user.setUpdatedAt(java.time.LocalDateTime.now().toString());
            userRepository.save(user);

            response.put("mensaje", "Contraseña cambiada correctamente");
            response.put("status", "success");
            return ResponseEntity.ok(response);

        } catch (ErrorApi e) {
            response.put("mensaje", e.getMessage());
            response.put("status", "error");
            return ResponseEntity.status(HttpStatus.valueOf(e.getCode())).body(response);
        } catch (Exception e) {
            response.put("mensaje", "Error al cambiar contraseña");
            response.put("error", e.getMessage());
            response.put("status", "error");
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Probar envío de código 2FA
     */
    @PostMapping("/test2fa")
    public ResponseEntity<Map<String, Object>> test2FA(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            Long userId = Long.valueOf(request.get("userId").toString());
            String email = (String) request.get("email");
            String phone = (String) request.get("phone");
            Integer type = Integer.valueOf(request.get("type").toString());
            var userOpt = userRepository.findById(userId);
            if (userOpt.isEmpty()) {
                throw new ErrorApi(404, "Usuario no encontrado");
            }

            String testCode = String.format("%06d", (int)(Math.random() * 1000000));

            if (type == 1) {
                // SMS test
                System.out.println("Código SMS enviado a " + phone + ": " + testCode);
                response.put("mensaje", "Código de prueba enviado por SMS a " + phone);
            } else {
                // Email test
                System.out.println("Código Email enviado a " + email + ": " + testCode);
                response.put("mensaje", "Código de prueba enviado por email a " + email);
            }
            // Code test
            response.put("testCode", testCode);
            response.put("status", "success");
            return ResponseEntity.ok(response);

        } catch (ErrorApi e) {
            response.put("mensaje", e.getMessage());
            response.put("status", "error");
            return ResponseEntity.status(HttpStatus.valueOf(e.getCode())).body(response);
        } catch (Exception e) {
            response.put("mensaje", "Error al enviar código de prueba");
            response.put("error", e.getMessage());
            response.put("status", "error");
            return ResponseEntity.badRequest().body(response);
        }
    }

}
