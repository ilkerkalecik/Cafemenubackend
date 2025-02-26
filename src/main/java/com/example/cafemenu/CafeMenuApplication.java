package com.example.cafemenu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


//@ComponentScan(basePackages = {"com.example.cafemenu"})
//@ComponentScan(basePackages = "com.example.cafemenu")
@SpringBootApplication
public class CafeMenuApplication {
    public static void main(String[] args) {
    	

        SpringApplication.run(CafeMenuApplication.class, args);
    }
}