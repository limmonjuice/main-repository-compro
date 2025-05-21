package com.limmon.midtermcoffee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.swing.*;

@SpringBootApplication
public class coffee_form_validation {

    public static void main(String[] args) {
        run();
        SpringApplication.run(coffee_form_validation.class, args);
    }
    public static void run(){
        String plainPassword = "secret";
        String hash = new BCryptPasswordEncoder().encode(plainPassword);
        System.out.println(hash);
    }
}


