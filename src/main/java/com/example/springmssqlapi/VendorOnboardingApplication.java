package com.example.springmssqlapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VendorOnboardingApplication {

    public static void main(String[] args) {
        SpringApplication.run(VendorOnboardingApplication.class, args);
    }

}
