package org.ayd.apimecahnicalworkshop.dto.userdto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {
    @NotEmpty(message = "El nombre de usuario no puede estar vacío")
    private String username;
    @NotEmpty(message = "La contraseña no puede estar vacía")
    private String password;
}
