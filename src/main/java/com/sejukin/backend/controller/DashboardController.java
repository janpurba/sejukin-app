package com.sejukin.backend.controller;

import com.sejukin.backend.model.Lookup;
import com.sejukin.backend.model.User;
import com.sejukin.backend.model.UserServiceTemplate;
import com.sejukin.backend.repository.CustomerRepository;
import com.sejukin.backend.repository.LookupRepository;
import com.sejukin.backend.repository.ServiceRecordRepository;
import com.sejukin.backend.repository.UserRepository;
import com.sejukin.backend.repository.UserServiceTemplateRepository;
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
import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired private CustomerRepository customerRepository;
    @Autowired private ServiceRecordRepository serviceRecordRepository;
    @Autowired private LookupRepository lookupRepository;
    @Autowired private UserServiceTemplateRepository userServiceTemplateRepository;
    @Autowired private UserRepository userRepository;

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
    public String showSettings(Model model, Principal principal) {
        model.addAttribute("intervalHari", DEFAULT_REMINDER_DAYS);
        model.addAttribute("activePage", "settings");

        // Ambil daftar Service Type yang ACTIVE
        List<Lookup> serviceTypes = lookupRepository.findByTypeAndStatusActive("serviceType", "active");
        model.addAttribute("serviceTypes", serviceTypes);

        // Ambil template pesan user yang login
        User user = userRepository.findByUsername(principal.getName()).orElseThrow();
        List<UserServiceTemplate> userTemplates = userServiceTemplateRepository.findByUserId(user.getId());
        model.addAttribute("userTemplates", userTemplates);

        return "setting"; // Perbaikan: nama file template adalah setting.html, bukan settings
    }

    // PROSES SIMPAN SETTING
    @PostMapping("/settings/save")
    public String saveSettings(@RequestParam("interval") int interval) {
        DEFAULT_REMINDER_DAYS = interval;
        return "redirect:/dashboard/settings?success";
    }

    @PostMapping("/settings/template/save")
    public String saveTemplate(@RequestParam Long serviceTypeId, 
                               @RequestParam String whatsappTemplate, 
                               @RequestParam(required = false, defaultValue = "90") Integer reminderDays,
                               Principal principal) {
        
        if (whatsappTemplate.length() > 200) {
            return "redirect:/dashboard/settings?error=Template terlalu panjang (maks 200 karakter)";
        }

        User user = userRepository.findByUsername(principal.getName()).orElseThrow();
        Lookup serviceType = lookupRepository.findById(serviceTypeId).orElseThrow();

        UserServiceTemplate template = userServiceTemplateRepository
                .findByUserIdAndServiceTypeId(user.getId(), serviceTypeId)
                .orElse(new UserServiceTemplate());
        
        template.setUser(user);
        template.setServiceType(serviceType);
        template.setWhatsappTemplate(whatsappTemplate);
        template.setReminderDays(reminderDays);
        
        userServiceTemplateRepository.save(template);

        return "redirect:/dashboard/settings?successTemplate";
    }
}
