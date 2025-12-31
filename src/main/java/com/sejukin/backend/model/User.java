package com.sejukin.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users") // Tabel pengguna
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Ini akan jadi tenantId nanti

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password; // Harus terenkripsi (BCrypt)

    private String role; // ROLE_ADMIN, ROLE_USER
}