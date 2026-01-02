package com.sejukin.backend.service;

import com.sejukin.backend.model.Customer;
import com.sejukin.backend.model.ServiceRecord;
import com.sejukin.backend.model.User;
import com.sejukin.backend.repository.CustomerRepository;
import com.sejukin.backend.repository.ServiceRecordRepository;
import com.sejukin.backend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class TransactionService {

    @Autowired private ServiceRecordRepository serviceRecordRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private CustomerRepository customerRepository;

    @Transactional // Jurus sakti: Save Transaksi Sukses + Logic Reminder Sukses, atau Batal Semua.
    public void createTransaction(ServiceRecord record, Long customerId, String username) {
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User error"));

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer tidak ditemukan"));

        // 1. Set Data Dasar
        record.setTenantId(currentUser.getId());
        record.setCustomer(customer);

        // 2. LOGIC BISNIS: Hitung Tanggal Reminder (H+90)
        // Kalau nanti mau ganti jadi H+60, cukup ubah di file ini saja.
        LocalDate reminderDate = record.getServiceDate().plusDays(90);
        record.setNextReminderDate(reminderDate);
        record.setReminderStatus("PENDING");

        // 3. Simpan
        serviceRecordRepository.save(record);
        log.info("Transaksi tersimpan. Reminder dijadwalkan tanggal: {}", reminderDate);
    }

    public List<ServiceRecord> getServiceHistory(Long customerId) {
        return serviceRecordRepository.findByCustomerIdOrderByServiceDateDesc(customerId);
    }
}