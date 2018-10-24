package com.example.demo;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import static java.util.Optional.ofNullable;

@Slf4j
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
//		SpringApplication.run(DemoApplication.class, args);
		SpringApplication app = new SpringApplication(DemoApplication.class);
		app.run(args);
	}

	@Bean
	ApplicationRunner init(Environment env) {
		return args -> {
			log.info("### Started with greeting template: {}",
					ofNullable(env.getProperty("greeting.template"))
							.orElseThrow(GreetingTemplateMissing::new));
		};
	}
}

class GreetingTemplateMissing extends RuntimeException implements ExitCodeGenerator {

	GreetingTemplateMissing() {
		super("Greeting template not defined!");
	}

	@Override
	public int getExitCode() {
		return 11;
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
@AllArgsConstructor
@EnableConfigurationProperties(GreetingConfig.class)
class GreetingService {

	private final GreetingConfig config;

	Greeting greet(String name) {
		return new Greeting(String.format(config.getTemplate(), name));
	}
}

@Data
@ConfigurationProperties(prefix = "greeting")
class GreetingConfig {
	private String template = "Hi %s";
}

@lombok.Value
class Greeting {

	private String message;

	@JsonCreator
	public Greeting(@JsonProperty("message") String message) {
		this.message = message;
	}
}
