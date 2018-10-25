package io.spring.lab.store;

import java.util.List;

import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestTemplate;

import io.spring.lab.cloud.ConditionalOnEurekaClient;
import io.spring.lab.store.item.ItemRepresentation;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.web.util.UriComponentsBuilder.fromUri;

@Slf4j
@Configuration
public class StoreCloudConfig {

    @Bean
    @ConditionalOnEurekaClient
    ApplicationRunner discoveryExample(DiscoveryClient client) {
        return args -> {
            ParameterizedTypeReference<List<ItemRepresentation>> responseType =
                    new ParameterizedTypeReference<List<ItemRepresentation>>() {};
            log.info("Warehouse instances found:");
            client.getInstances("warehouse").forEach(serviceInstance -> {
                log.info(" - {}", serviceInstance.getUri());
                log.info(" - metadata:");
                serviceInstance.getMetadata().entrySet().forEach(entry ->
                        log.info(" -- {}: {}", entry.getKey(), entry.getValue()));
                log.info(" - items found:");
                new RestTemplate()
                        .exchange(fromUri(serviceInstance.getUri()).path("/items").build().toUri(), GET, null, responseType)
                        .getBody()
                        .forEach(item -> log.info(" -- {}", item));
            });
        };
    }
}
