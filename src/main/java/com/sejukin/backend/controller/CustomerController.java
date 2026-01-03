package com.sejukin.backend.controller;

import com.sejukin.backend.model.Customer;
import com.sejukin.backend.model.User;
import com.sejukin.backend.model.UserServiceTemplate;
import com.sejukin.backend.repository.CustomerRepository;
import com.sejukin.backend.repository.UserRepository;
import com.sejukin.backend.repository.UserServiceTemplateRepository;
import com.sejukin.backend.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired private CustomerRepository customerRepository;
    @Autowired private CustomerService customerService;
    @Autowired private UserServiceTemplateRepository userServiceTemplateRepository;
    @Autowired private UserRepository userRepository;

    @GetMapping
    public String listCustomers(Model model, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow();
        
        // Ambil daftar layanan yang dimiliki user untuk dropdown
        List<UserServiceTemplate> userServices = userServiceTemplateRepository.findByUserId(user.getId());
        
        model.addAttribute("customers", customerService.getAllCustomersByUsername(principal.getName()));
        model.addAttribute("userServices", userServices);
        model.addAttribute("username", principal.getName());
        model.addAttribute("activePage", "customer");
        return "customer";
    }

    @PostMapping("/save")
    public String saveCustomer(@ModelAttribute Customer customer, 
                               @RequestParam(required = false) Long serviceTypeId,
                               Principal principal) {
        // "Service, tolong simpan data ini"
        customerService.saveCustomer(customer, principal.getName(), serviceTypeId);
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