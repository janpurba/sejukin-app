package com.sejukin.backend.controller;

import com.sejukin.backend.model.Customer;
import com.sejukin.backend.model.ServiceRecord;
import com.sejukin.backend.repository.CustomerRepository;
import com.sejukin.backend.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired private TransactionService transactionService;
    @Autowired private CustomerRepository customerRepository;

    @GetMapping("/input-service/{customerId}")
    public String showServiceForm(@PathVariable Long customerId, Model model) {
        // Karena cuma baca data untuk form, boleh akses repo dikit atau via service (bebas)
        // Untuk refactor kali ini, kita skip bagian view form biar cepat.
        // Asumsi logic view form masih sama seperti sebelumnya.
        ServiceRecord record = new ServiceRecord();
        model.addAttribute("serviceRecord", record);
        model.addAttribute("customerId", customerId); // Lempar ID ke form
        Customer customer = customerRepository.findById(customerId).orElse(new Customer());
        model.addAttribute("customerName", customer.getName());
        return "service-form";
    }

    @PostMapping("/save-service")
    public String saveService(@ModelAttribute ServiceRecord serviceRecord,
                              @RequestParam Long customerId, // Pastikan ada input hidden name="customerId" di HTML
                              Principal principal) {

        transactionService.createTransaction(serviceRecord, customerId, principal.getName());
        return "redirect:/customer?successServis";
    }

    @GetMapping("/history/{customerId}")
    public String showHistory(@PathVariable Long customerId, Model model) {
        List<ServiceRecord> history = transactionService.getServiceHistory(customerId);
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
        
        model.addAttribute("history", history);
        model.addAttribute("customer", customer);
        return "service-history";
    }
}
