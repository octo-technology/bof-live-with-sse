package com.example.demo.infrastructure.repositories;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class ScoreEndpoint {

    public Integer generateRandomInteger() {
        Random rand = new Random();
        Integer max = 5;
        Integer min = 2;
        return rand.nextInt((max - min) + 1) + min;
    }
}
