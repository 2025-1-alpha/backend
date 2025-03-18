package com.geulowup.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GeulowupBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeulowupBackendApplication.class, args);
    }

}
