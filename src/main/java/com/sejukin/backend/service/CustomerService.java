package com.sejukin.backend.service;

import com.sejukin.backend.model.Customer;
import com.sejukin.backend.model.ServiceRecord;
import com.sejukin.backend.model.User;
import com.sejukin.backend.model.UserServiceTemplate;
import com.sejukin.backend.repository.CustomerRepository;
import com.sejukin.backend.repository.ServiceRecordRepository;
import com.sejukin.backend.repository.UserRepository;
import com.sejukin.backend.repository.UserServiceTemplateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class CustomerService {

    @Autowired private CustomerRepository customerRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private UserServiceTemplateRepository userServiceTemplateRepository;
    @Autowired private ServiceRecordRepository serviceRecordRepository;

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
    public void saveCustomer(Customer customer, String username, Long serviceTypeId) {
        User currentUser = getUserByUsername(username);
        customer.setTenantId(currentUser.getId());

        Customer savedCustomer = customerRepository.save(customer);
        log.info("Berhasil menyimpan pelanggan: {} untuk User ID: {}", customer.getName(), currentUser.getId());

        // Jika ada serviceTypeId yang dipilih saat tambah pelanggan
        if (serviceTypeId != null) {
            createInitialServiceRecord(savedCustomer, currentUser, serviceTypeId);
        }
    }

    private void createInitialServiceRecord(Customer customer, User user, Long serviceTypeId) {
        UserServiceTemplate template = userServiceTemplateRepository
                .findByUserIdAndServiceTypeId(user.getId(), serviceTypeId)
                .orElse(null);

        int reminderDays = (template != null && template.getReminderDays() != null) ? template.getReminderDays() : 90;
        
        ServiceRecord record = new ServiceRecord();
        record.setTenantId(user.getId());
        record.setCustomer(customer);
        record.setServiceDate(LocalDate.now());
        record.setDescription("Pendaftaran Pelanggan Baru - " + (template != null ? template.getServiceType().getDescription() : "Layanan Umum"));
        record.setPrice(0.0); // Harga 0 karena cuma inisialisasi reminder
        record.setNextReminderDate(LocalDate.now().plusDays(reminderDays));
        record.setReminderStatus("PENDING");
        
        serviceRecordRepository.save(record);
        log.info("Initial service record created for customer: {}, reminder in {} days", customer.getName(), reminderDays);
    }
}