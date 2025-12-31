package com.sejukin.backend.model;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "service_records")
public class ServiceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tenant_id", nullable = false)
    private Long tenantId;

    // Relasi ke Customer (Biar tau ini servis punya siapa)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "service_date", nullable = false)
    private LocalDate serviceDate; // Tgl Servis Dilakukan

    @Column(nullable = false)
    private String description; // Misal: "Cuci AC 2 Unit + Isi Freon"

    @Column(nullable = false)
    private Double price; // Harga Total

    // INI KOLOM PALING PENTING (Target Reminder)
    @Column(name = "next_reminder_date")
    private LocalDate nextReminderDate;

    // Status Reminder: PENDING, SENT, CANCELLED
    @Column(name = "reminder_status")
    private String reminderStatus = "PENDING";

    @CreationTimestamp
    private LocalDateTime createdAt;
}