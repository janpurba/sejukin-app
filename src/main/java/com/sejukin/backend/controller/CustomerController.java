package com.sejukin.backend.controller;

import com.sejukin.backend.model.Customer;
import com.sejukin.backend.repository.CustomerRepository;
import com.sejukin.backend.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired private CustomerRepository customerRepository;
    @Autowired private CustomerService customerService;

    @GetMapping
    public String listCustomers(Model model, Principal principal) {
        // Cukup satu baris: "Service, tolong ambilkan data buat user ini"
        model.addAttribute("customers", customerService.getAllCustomersByUsername(principal.getName()));
        model.addAttribute("username", principal.getName());
        model.addAttribute("activePage", "customer");
        return "customer";
    }

    @PostMapping("/save")
    public String saveCustomer(@ModelAttribute Customer customer, Principal principal) {
        // "Service, tolong simpan data ini"
        customerService.saveCustomer(customer, principal.getName());
        return "redirect:/customer?saved";
    }

    @PostMapping("/update")
    public String updateCustomer(@ModelAttribute Customer customer) {
        Customer existingCustomer = customerRepository.findById(customer.getId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        
        existingCustomer.setPhoneNumber(customer.getPhoneNumber());
        existingCustomer.setAddress(customer.getAddress());
        // Nama tidak diupdate sesuai request, tapi kalau mau bisa ditambahkan:
        // existingCustomer.setName(customer.getName());
        
        customerRepository.save(existingCustomer);
        return "redirect:/customer?updated";
    }
}