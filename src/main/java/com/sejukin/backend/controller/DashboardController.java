package com.sejukin.backend.controller;

import com.sejukin.backend.repository.CustomerRepository;
import com.sejukin.backend.repository.ServiceRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ServiceRecordRepository serviceRecordRepository;

    // Simpan setting interval di memori sementara (Nanti bisa dipindah ke DB)
    public static int DEFAULT_REMINDER_DAYS = 90;

    // 1. MENU DASHBOARD
    @GetMapping
    public String showDashboard(Model model) {

        // 2. AMBIL USERNAME DARI BRANKAS SECURITY (Paling Aman)
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); // Pasti dapat nama user yg login (misal: admin)

        // 3. Masukkan ke Model
        model.addAttribute("username", username);
        long totalPelanggan = customerRepository.count();
        long totalPengingat = serviceRecordRepository.countByReminderStatus("SENT");
        long totalPending = serviceRecordRepository.countByReminderStatus("PENDING");
        long totalLayanan = serviceRecordRepository.count();
        
        model.addAttribute("totalPelanggan", totalPelanggan);
        model.addAttribute("totalPengingat", totalPengingat);
        model.addAttribute("totalPending", totalPending);
        model.addAttribute("totalLayanan", totalLayanan);
        model.addAttribute("activePage", "dashboard");

        return "dashboard";
    }

    @GetMapping("/settings")
    public String showSettings(Model model) {
        model.addAttribute("intervalHari", DEFAULT_REMINDER_DAYS);
        model.addAttribute("activePage", "settings");
        return "settings";
    }

    // PROSES SIMPAN SETTING
    @PostMapping("/settings/save")
    public String saveSettings(@RequestParam("interval") int interval) {
        DEFAULT_REMINDER_DAYS = interval;
        return "redirect:/dashboard/settings?success";
    }
}