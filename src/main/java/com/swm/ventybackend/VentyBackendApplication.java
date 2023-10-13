package com.swm.ventybackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// test
@RestController
@SpringBootApplication
public class VentyBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(VentyBackendApplication.class, args);
	}

	@GetMapping
	public String helloWorld() {
		return "23.10.11 Ver.";
	}
}
