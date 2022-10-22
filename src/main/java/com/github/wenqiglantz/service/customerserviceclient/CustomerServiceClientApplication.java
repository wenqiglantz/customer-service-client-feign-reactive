package com.github.wenqiglantz.service.customerserviceclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@EnableReactiveFeignClients
@SpringBootApplication
public class CustomerServiceClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceClientApplication.class, args);
	}
}
