package com.clody.support;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SupportApplication {
    public static void main(String[] args) {
        System.setProperty("spring.config.name", "application,application-support");
        SpringApplication.run(SupportApplication.class, args);
    }

}
