package com.flashcards.FlashCards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class FlashCardsApplication {

    public static void main(String[] args) {
        System.out.println("DB: " + System.getenv("DB_DATABASE"));
        System.out.println("USER: " + System.getenv("DB_USER"));
        System.out.println("PASSWORD: " + System.getenv("DB_PASSWORD"));

        ApplicationContext context = SpringApplication.run(FlashCardsApplication.class, args);
    }

}
