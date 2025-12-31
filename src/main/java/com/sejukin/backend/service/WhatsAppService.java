package com.sejukin.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class WhatsAppService {

    @Value("${fonnte.api.token}")
    private String token;

    private final RestTemplate restTemplate = new RestTemplate();

    public void sendMessage(String targetNumber, String message) {
        String url = "https://api.fonnte.com/send";

        // 1. Siapkan Header (Token Otorisasi)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA); // Fonnte minta form-data
        headers.set("Authorization", token);

        // 2. Siapkan Body (Pesan & Tujuan)
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("target", targetNumber);
        body.add("message", message);
        // body.add("countryCode", "62"); // Opsional, kalau nomor database blm ada 62

        // 3. Bungkus Header & Body
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        try {
            // 4. KIRIM! (POST Request)
            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
            System.out.println("Respon Fonnte: " + response.getBody());
        } catch (Exception e) {
            System.err.println("Gagal kirim WA: " + e.getMessage());
        }
    }
}