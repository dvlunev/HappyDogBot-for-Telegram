package com.team4.happydogbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class HappyDogBotApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(HappyDogBotApplication.class, args);

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

    }
}

