package com.medishare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MediShareApplication {

	public static void main(String[] args) {
		SpringApplication.run(MediShareApplication.class, args);
	}

}
