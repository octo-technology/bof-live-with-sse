package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Clock;

import static java.time.ZoneId.of;

@SpringBootApplication
public class CleanArchitectureDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CleanArchitectureDemoApplication.class, args);
    }

    @Bean
    public Clock systemClockAtEuropeParisZone() {
        return Clock.system(of("Europe/Paris"));
    }
}
