package com.example_jelle.backenddico;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * This is the main integration test class for the Backend Dico application.
 * The @SpringBootTest annotation tells Spring Boot to look for a main configuration class
 * (one with @SpringBootApplication) and use that to start a Spring application context.
 */
@SpringBootTest
class BackendDicoApplicationTests {

    /**
     * This test method checks if the application context can be loaded successfully.
     * If the application context fails to start for any reason (e.g., configuration errors,
     * bean creation issues), this test will fail.
     */
    @Test
    void contextLoads() {
    }

}
