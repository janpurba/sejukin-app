package com.sejukin.backend.component;

import com.sejukin.backend.model.User;
import com.sejukin.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Cek apakah user admin sudah ada? Kalau belum, buat.
        if (userRepository.findByUsername("admin").isEmpty()) {
            User user = new User();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("admin123")); // Passwordnya: admin123
            user.setRole("ADMIN");
            userRepository.save(user);
            System.out.println(">>> User ADMIN berhasil dibuat. Login: admin / admin123");
        }
    }
}