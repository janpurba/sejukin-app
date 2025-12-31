package com.sejukin.backend.controller;

import com.sejukin.backend.model.Customer;
import com.sejukin.backend.model.ServiceRecord;
import com.sejukin.backend.model.User;
import com.sejukin.backend.repository.CustomerRepository;
import com.sejukin.backend.repository.ServiceRecordRepository;
import com.sejukin.backend.repository.UserRepository;
import com.sejukin.backend.service.CustomerService;
import com.sejukin.backend.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
public class CustomerController {

    @Autowired private CustomerRepository customerRepository;
    @Autowired private ServiceRecordRepository serviceRecordRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private CustomerService customerService;

    @GetMapping("/customer")
    public String listCustomers(Model model, Principal principal) {
        // Cukup satu baris: "Service, tolong ambilkan data buat user ini"
        model.addAttribute("customers", customerService.getAllCustomersByUsername(principal.getName()));
        model.addAttribute("username", principal.getName());
        return "customer-list";
    }

    @GetMapping("/add-customer")
    public String showForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer-form";
    }

    @PostMapping("/save-customer")
    public String saveCustomer(@ModelAttribute Customer customer, Principal principal) {
        // "Service, tolong simpan data ini"
        customerService.saveCustomer(customer, principal.getName());
        return "redirect:/customer?saved";
    }

    private User getLoggedInUser(Principal principal) {
        String username = principal.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));
    }
}