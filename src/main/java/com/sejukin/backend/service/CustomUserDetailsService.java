package com.sejukin.backend.service;

import com.sejukin.backend.model.User;
import com.sejukin.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User tidak ditemukan"));

        // Validasi Status User
        if (user.getStatus() != null && !"ACTIVE".equalsIgnoreCase(user.getStatus().getCode())) {
            throw new DisabledException("Akun Anda tidak aktif. Silakan hubungi administrator.");
        }

        // Konversi User Entity kita ke User Spring Security
        // Menggunakan level sebagai role jika ada, atau default "USER"
        String role = (user.getLevel() != null) ? user.getLevel().getCode() : "USER";

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(role)
                .build();
    }
}