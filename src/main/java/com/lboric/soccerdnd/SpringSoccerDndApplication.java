package com.lboric.soccerdnd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@SpringBootApplication
@OpenAPIDefinition
public class SpringSoccerDndApplication {

	public static void main(final String[] args) {
		SpringApplication.run(SpringSoccerDndApplication.class, args);
	}

}
