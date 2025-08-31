package org.ayd.apimecahnicalworkshop.services;

import org.ayd.apimecahnicalworkshop.dto.userdto.RegisterClientDTO;
import org.ayd.apimecahnicalworkshop.dto.userdto.UserDTO;
import org.ayd.apimecahnicalworkshop.dto.userdto.UserLoginDTO;
import org.ayd.apimecahnicalworkshop.dto.userdto.UserResponseDTO;
import org.ayd.apimecahnicalworkshop.entities.Client;
import org.ayd.apimecahnicalworkshop.entities.Supplier;
import org.ayd.apimecahnicalworkshop.entities.User;
import org.ayd.apimecahnicalworkshop.repositories.ClientRepository;
import org.ayd.apimecahnicalworkshop.repositories.SupplierRepository;
import org.ayd.apimecahnicalworkshop.repositories.UserRepository;
import org.ayd.apimecahnicalworkshop.utils.Encriptation;
import org.ayd.apimecahnicalworkshop.utils.ErrorApi;
import org.ayd.apimecahnicalworkshop.utils.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.stream.Collectors;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private SupplierRepository supplierRepository;

    private Encriptation encriptation;
    public UserService() {
        this.encriptation = new Encriptation();
    }

    /**
     * Create a new user
     * @param user - UserDTO
     * @return UserResponseDTO
     */
    public UserResponseDTO saveUser(UserDTO user, String typeSupplier) {
        if (repository.existsByUsername(user.getUsername())) {
            throw new ErrorApi(409, "El nombre de usuario ya existe");
        }
        if (repository.findByEmail(user.getEmail()) != null) {
            throw new ErrorApi(409, "El correo electrónico ya existe");
        }

        User saveUser = new User();
        saveUser.setRolId(user.getRolId());
        setUser(saveUser,
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getPhone(),
                user.getName(),
                user.getLastName());

        if(user.getRolId().equals(Roles.SUPPLIER.getId())){
            Supplier supplier = new Supplier();
            supplier.setUserId(saveUser.getUserId());
            supplier.setTypeSupplier(typeSupplier);
            supplierRepository.save(supplier);
        }

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

    /**
     * Register a new client
     * @param user - RegisterClientDTO
     * @return UserResponseDTO
     */
    public UserResponseDTO registerClient(RegisterClientDTO user) {
        if (repository.existsByUsername(user.getUsername())) {
            throw new ErrorApi(409, "El nombre de usuario ya existe");
        }
        if (repository.findByEmail(user.getEmail()) != null) {
            throw new ErrorApi(409, "El correo electrónico ya existe");
        }
        User saveUser = new User();
        saveUser.setRolId(Roles.CLIENT.getId()); // Client role
        setUser(saveUser,
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getPhone(),
                user.getName(),
                user.getLastName()
                );

        Client client = new Client();
        client.setUserId(saveUser.getUserId());
        client.setNit(user.getNit());
        client.setAddress(user.getAddress());
        clientRepository.save(client);

        return new UserResponseDTO(
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

    private void setUser(User saveUser,
                         String username,
                         String password,
                         String email,
                         String phone,
                         String name,
                         String lastName) {
        saveUser.setUsername(username);
        saveUser.setPassword(encriptation.encrypt(password));
        saveUser.setActive(true);
        saveUser.setEmail(email);
        saveUser.setPhone(phone);
        saveUser.setName(name);
        saveUser.setLastName(lastName);
        saveUser.setTwoFactorAuth(false);
        saveUser.setTypeTwoFactorId(1L); // email by default
        saveUser.setCreatedAt(LocalDate.now().toString());
        saveUser.setUpdatedAt(LocalDate.now().toString());
        repository.save(saveUser); // Save user to the database
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

    /**
     * Update user
     * @param user - user
     * @return UserResponseDTO
     */
    public UserResponseDTO updateUser(UserResponseDTO user) {
        return mapToDTO(repository.save(mapToEntity(user)));
    }

    /**
     * User login
     * @param userLogin - UserLoginDTO
     * @return UserResponseDTO
     */
    public UserResponseDTO userLogin(UserLoginDTO userLogin) {
        User user = repository.findByUsername(userLogin.getUsername());
        if (user == null) {
            throw new ErrorApi(404, "Usuario no encontrado");
        }
        if (!encriptation.matches(userLogin.getPassword(), user.getPassword())) {
            throw new ErrorApi(401, "Contraseña incorrecta");
        }
        //verificar si tiene 2fa activado y retornar un mensaje indicando que debe verificar su 2fa
        //verificar el método de 2fa y enviar el código
        //todo: implementar 2fa
        //por ahora solo retornamos el usuario
        return new UserResponseDTO(
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
        );
    }

    public List<UserResponseDTO> getUsersByActive(boolean active) {
        List<User> users = repository.getUserByIsActive(active);
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

    /**
     *
     * @param user - User
     * @return - UserResponseDTO
     */
    private UserResponseDTO mapToDTO(User user) {
        return new UserResponseDTO(
                user.getUserId(),
                user.getRolId(),
                user.getUsername(),
                user.isActive(),
                user.getEmail(),
                user.getPhone(),
                user.getName(),
                user.getName(),
                user.isTwoFactorAuth(),
                user.getTypeTwoFactorId()
        );
    }

    /**
     * Map UserResponseDTO to User
     * @param user - UserResponseDTO
     * @return User
     */
    private User mapToEntity(UserResponseDTO user) {
        User aux = repository.findById(user.getUserId()).get();
        aux.setRolId(user.getRolId());
        aux.setUsername(user.getUsername());
        aux.setActive(user.isActive());
        aux.setEmail(user.getEmail());
        aux.setPhone(user.getPhone());
        aux.setName(user.getName());
        aux.setLastName(user.getLastName());
        aux.setTwoFactorAuth(user.isTwoFactorAuth());
        aux.setTypeTwoFactorId(user.getTypeTwoFactorId());
        return aux;
    }


}
