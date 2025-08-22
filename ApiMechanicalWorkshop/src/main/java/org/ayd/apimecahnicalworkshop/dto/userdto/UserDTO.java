package org.ayd.apimecahnicalworkshop.dto.userdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @NotEmpty(message = "Role ID is required")
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

}
