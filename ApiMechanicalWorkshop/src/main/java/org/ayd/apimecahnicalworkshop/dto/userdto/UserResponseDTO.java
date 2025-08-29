package org.ayd.apimecahnicalworkshop.dto.userdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private Long userId;
    private Long rolId;
    private String username;
    private boolean active;
    private String email;
    private String phone;
    private String name;
    private String lastName;
    private boolean twoFactorAuth;
    private Long typeTwoFactorId;
}
