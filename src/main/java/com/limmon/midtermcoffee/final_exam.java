package com.limmon.midtermcoffee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class final_exam {

    public static void main(String[] args) {
        run();
        SpringApplication.run(final_exam.class, args);
    }
    public static void run(){
        String plainPassword = "akonalang";
        String hash = new BCryptPasswordEncoder().encode(plainPassword);
        System.out.println(hash);
    }
}


