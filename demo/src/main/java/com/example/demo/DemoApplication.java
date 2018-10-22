package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
//		SpringApplication.run(DemoApplication.class, args);
		SpringApplication app = new SpringApplication(DemoApplication.class);
		app.run(args);
	}
}

@RestController
@AllArgsConstructor
class GreetingController {

	private GreetingService service;

	@GetMapping("/greet/{name}")
	Greeting sayHello(@PathVariable String name) {
		return service.greet(name);
	}
}

@Service
class GreetingService {

	@Value("${greeting.template:Hi %s}")
	String template;

	Greeting greet(String name) {
		return new Greeting(String.format(template, name));
	}
}

@lombok.Value
class Greeting {

	private String message;

	@JsonCreator
	public Greeting(@JsonProperty("message") String message) {
		this.message = message;
	}
}
