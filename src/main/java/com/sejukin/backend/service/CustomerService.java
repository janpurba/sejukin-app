package com.sejukin.backend.service;

import com.sejukin.backend.model.Customer;
import com.sejukin.backend.model.User;
import com.sejukin.backend.repository.CustomerRepository;
import com.sejukin.backend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j // Ini pengganti System.out.println (Logging canggih)
@Service
public class CustomerService {

    @Autowired private CustomerRepository customerRepository;
    @Autowired private UserRepository userRepository;

    // Helper: Cari User berdasarkan Username (Private, cuma dipakai internal class ini)
    private User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User tidak valid"));
    }

    // MENU 1: Ambil semua pelanggan milik user tertentu
    public List<Customer> getAllCustomersByUsername(String username) {
        User currentUser = getUserByUsername(username);
        log.info("Mengambil data pelanggan untuk user: {}", username);
        return customerRepository.findByTenantId(currentUser.getId());
    }

    // MENU 2: Simpan Pelanggan Baru
    @Transactional // Pastikan aman
    public void saveCustomer(Customer customer, String username) {
        User currentUser = getUserByUsername(username);

        // Logic Tenant ID pindah ke sini (Controller tidak perlu tahu)
        customer.setTenantId(currentUser.getId());

        customerRepository.save(customer);
        log.info("Berhasil menyimpan pelanggan: {} untuk User ID: {}", customer.getName(), currentUser.getId());
    }
}