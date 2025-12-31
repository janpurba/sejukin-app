package com.sejukin.backend.component;

import com.sejukin.backend.model.ServiceRecord;
import com.sejukin.backend.repository.ServiceRecordRepository;
import com.sejukin.backend.service.WhatsAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class WhatsAppReminderScheduler {

    @Autowired
    private ServiceRecordRepository serviceRecordRepository;

    @Autowired
    private WhatsAppService whatsAppService;

    // Jalan setiap 10 detik (untuk testing malam ini)
    // Nanti kalau production, ganti jadi cron = "0 0 8 * * *" (Jam 8 Pagi Tiap Hari)
    @Scheduled(fixedRate = 10000)
    public void checkAndSendReminders() {
        LocalDate today = LocalDate.now();

        // Cari yang statusnya PENDING
        List<ServiceRecord> dueServices = serviceRecordRepository.findByNextReminderDateAndReminderStatus(today, "PENDING");

        if (!dueServices.isEmpty()) {
            System.out.println("=== MENGIRIM PESAN REAL WA ===");

            for (ServiceRecord record : dueServices) {
                String nomorHP = record.getCustomer().getPhoneNumber();
                String nama = record.getCustomer().getName();

                // Susun Pesan yang Cantik
                String pesan = "Halo Kak *" + nama + "*! 👋\n\n" +
                        "Sekedar mengingatkan, AC kakak sudah waktunya diservis nih (sudah 3 bulan).\n" +
                        "Servis terakhir: " + record.getDescription() + "\n\n" +
                        "Mau dijadwalkan kunjungan teknisi minggu ini? Balas 'YA' ya kak.";

                // EKSEKUSI KIRIM
                whatsAppService.sendMessage(nomorHP, pesan);

                // Update status biar gak ke-kirim 2x
                record.setReminderStatus("SENT");
                serviceRecordRepository.save(record);
            }
        }
    }
}