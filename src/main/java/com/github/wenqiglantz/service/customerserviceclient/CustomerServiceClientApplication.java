package com.github.wenqiglantz.service.customerserviceclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@EnableReactiveFeignClients
@SpringBootApplication
@ComponentScan("reactivefeign.spring.config")
public class CustomerServiceClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceClientApplication.class, args);
	}
}
