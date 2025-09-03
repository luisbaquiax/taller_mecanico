package org.ayd.apimecahnicalworkshop.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clients")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id", nullable = false)
    private Long clientId;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "nit", nullable = false, length = 13)
    private String nit;
    @Column(name = "address", nullable = false, length = 200)
    private String address;
}
