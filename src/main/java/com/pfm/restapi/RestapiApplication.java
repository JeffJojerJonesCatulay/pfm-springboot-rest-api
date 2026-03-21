package com.pfm.restapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class RestapiApplication {
	public static void main(String[] args) {
		SpringApplication.run(RestapiApplication.class, args);
	}
}
