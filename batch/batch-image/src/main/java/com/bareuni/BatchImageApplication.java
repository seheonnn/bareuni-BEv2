package com.bareuni;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackages = {"com.bareuni"})
public class BatchImageApplication {
	public static void main(String[] args) {
		SpringApplication.run(BatchImageApplication.class, args);
	}
}
