package com.sejukin.backend.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "status", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"code", "type"})
})
public class Status implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code;

    private String description;
    
    @Column(nullable = false)
    private String type;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}