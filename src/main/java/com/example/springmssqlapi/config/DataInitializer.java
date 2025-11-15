package com.example.springmssqlapi.config;

import com.example.springmssqlapi.entity.Vendor;
import com.example.springmssqlapi.repository.VendorRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {
    
    private final VendorRepository vendorRepository;
    
    @Override
    public void run(String... args) throws Exception {
        initializeVendors();
    }
    
    private void initializeVendors() {
        if (vendorRepository.count() == 0) {
            // Create sample vendors for testing
            Vendor vendor1 = new Vendor();
            vendor1.setEmail("test@vendor1.com");
            vendor1.setName("Test Vendor 1");
            vendor1.setPhoneNumber("+1234567890");
            vendor1.setIsActive(true);
            vendor1.setCreatedAt(LocalDateTime.now());
            vendor1.setUpdatedAt(LocalDateTime.now());
            
            Vendor vendor2 = new Vendor();
            vendor2.setEmail("test@vendor2.com");
            vendor2.setName("Test Vendor 2");
            vendor2.setPhoneNumber("+1987654321");
            vendor2.setIsActive(true);
            vendor2.setCreatedAt(LocalDateTime.now());
            vendor2.setUpdatedAt(LocalDateTime.now());
            
            vendorRepository.save(vendor1);
            vendorRepository.save(vendor2);
            
            log.info("Sample vendors created for testing purposes");
        }
    }
}