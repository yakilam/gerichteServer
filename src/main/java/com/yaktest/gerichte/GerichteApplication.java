package com.yaktest.gerichte;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@RestController
public class GerichteApplication {

	public static void main(String[] args) {
		SpringApplication.run(GerichteApplication.class, args);
	}

	@GetMapping("/root")
	public String apiRoot() {
		return "Hello World";
	}

}
