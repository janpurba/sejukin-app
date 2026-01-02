package com.sejukin.backend.service;

import com.sejukin.backend.model.Customer;
import com.sejukin.backend.model.User;
import com.sejukin.backend.repository.CustomerRepository;
import com.sejukin.backend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@Slf4j
@Service
public class CustomerService {

    @Autowired private CustomerRepository customerRepository;
    @Autowired private UserRepository userRepository;

    // Helper: Cari User berdasarkan Username (Private, cuma dipakai internal class ini)
    @Cacheable(value = "userDetailsRedis")
    private User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User tidak valid"));
    }

    // MENU 1: Ambil semua pelanggan milik user tertentu
    @Cacheable(value = "userDetailsRedis")
    public List<Customer> getAllCustomersByUsername(String username) {
        User currentUser = getUserByUsername(username);
        log.info("Mengambil data pelanggan untuk user: {}", username);
        return customerRepository.findByTenantId(currentUser.getId());
    }

    @Transactional // Pastikan aman
    @CacheEvict(value = "userDetailsRedis", allEntries = true)
    public void saveCustomer(Customer customer, String username) {
        User currentUser = getUserByUsername(username);
        customer.setTenantId(currentUser.getId());

        customerRepository.save(customer);
        log.info("Berhasil menyimpan pelanggan: {} untuk User ID: {}", customer.getName(), currentUser.getId());
    }
}