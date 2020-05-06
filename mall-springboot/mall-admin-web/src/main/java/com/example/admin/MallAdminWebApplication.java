package com.example.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example")
public class MallAdminWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(MallAdminWebApplication.class, args);
	}

}
