package com.example.springmssqlapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = VendorOnboardingApplication.class)
@ActiveProfiles("test")
class VendorOnboardingApplicationTests {

    @Test
    void contextLoads() {
        // This test ensures that the Spring context loads successfully
        // It's a simple smoke test to verify basic Spring Boot configuration
    }

}
