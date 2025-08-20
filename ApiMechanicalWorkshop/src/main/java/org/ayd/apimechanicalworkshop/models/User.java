package org.ayd.apimechanicalworkshop.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    @Column(unique = true, nullable = false)
    private int userId;
    private int rolId;
    private String username;
    private String password;
    private boolean isActive;
    private String email;
    private String phone;
    private String name;
    private String lastName;
    private String createdAt;
    private String updatedAt;
    private boolean twoFactorAuth;
    private String twoFactorMethod;
}
