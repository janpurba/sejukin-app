package com.sejukin.backend.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "users") // Nama tabel di database
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password; // Akan disimpan dalam bentuk terenkripsi (BCrypt)

    @ManyToOne
    @JoinColumn(name = "level_id")
    private Lookup level;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    // Constructor Kosong
    public User() {}

    // Constructor Isi
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Lookup getLevel() { return level; }
    public void setLevel(Lookup level) { this.level = level; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}