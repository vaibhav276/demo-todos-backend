package com.demo.demotodos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@OpenAPIDefinition(
	info = @Info(
		title = "Demo Todos Service",
		description = "Demo Todos Service REST API documentation",
		version = "v1",
		contact = @Contact(
			name = "Vaibhav Pujari",
			email = "vaibhav.pujari@outlook.com",
			url = "https://github.com/vaibhav276"
		),
		license = @License(
			name = "MIT"
		)
	),
	externalDocs = @ExternalDocumentation(
		description =  "Demo Todos Service REST API Documentation",
		url = "https://localhost:8080/swagger-ui/index.html"
	)
)
public class DemoTodosApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoTodosApplication.class, args);
	}

}
