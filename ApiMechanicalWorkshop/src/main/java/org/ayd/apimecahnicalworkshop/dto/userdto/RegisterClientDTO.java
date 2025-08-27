package org.ayd.apimecahnicalworkshop.dto.userdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterClientDTO {
    @NotNull(message = "Role ID is required")
    private Long rolId;

    @NotEmpty(message = "Username is required")
    private String username;

    @NotEmpty(message = "Password is required")
    private String password;

    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty(message = "Phone is required")
    @Length(min = 8, max = 8, message = "Phone number invalid")
    private String phone;

    private String name;

    private String lastName;

    @NotEmpty(message = "NIT is required")
    @Length(min = 8, max = 8, message = "NIT must be 13 characters long")
    private String nit;

    @NotEmpty(message = "Address is required")
    private String address;
}
