package com.sejukin.backend.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_service_template", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "service_type_id"})
})
public class UserServiceTemplate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "service_type_id", nullable = false)
    private Lookup serviceType;

    @Column(name = "whatsapp_template", length = 200)
    private String whatsappTemplate;

    @Column(name = "reminder_days")
    private Integer reminderDays;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Lookup getServiceType() { return serviceType; }
    public void setServiceType(Lookup serviceType) { this.serviceType = serviceType; }
    public String getWhatsappTemplate() { return whatsappTemplate; }
    public void setWhatsappTemplate(String whatsappTemplate) { this.whatsappTemplate = whatsappTemplate; }
    public Integer getReminderDays() { return reminderDays; }
    public void setReminderDays(Integer reminderDays) { this.reminderDays = reminderDays; }
}
