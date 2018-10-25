package io.spring.lab.warehouse;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class WarehouseApplication {

	public static final String INSTANCE_ID = "info.instanceId";

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(WarehouseApplication.class);
		app.setDefaultProperties(defaultProperties());
		app.run(args);
	}

	private static Map<String, Object> defaultProperties() {
		Map<String, Object> props = new HashMap<>();
		props.put(INSTANCE_ID, UUID.randomUUID().toString().replaceAll("-", ""));
		return props;
	}

	@Bean
	ApplicationRunner instanceIdLogger(Environment environment) {
		return args ->
				log.info("instanceId: {}", environment.getProperty(INSTANCE_ID, "UNDEFINED"));
	}
}
