package com.sejukin.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SejukinAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SejukinAppApplication.class, args);
	}

}
