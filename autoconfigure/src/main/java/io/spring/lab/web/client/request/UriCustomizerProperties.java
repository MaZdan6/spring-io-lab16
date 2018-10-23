package io.spring.lab.web.client.request;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

import static java.util.Optional.ofNullable;
import static org.springframework.web.util.UriComponentsBuilder.fromUri;

@Data
@ConfigurationProperties(prefix = "uri.customizer")
public class UriCustomizerProperties implements UriCustomizer {

    /**
     * URI rewriting map from service ID to host & port pairs.
     */
    private Map<String, UriProperties> rewrite = new HashMap<>();

    @Override
    public URI rewrite(URI uri) {
        String serviceId = uri.getHost();
        return ofNullable(rewrite.get(serviceId))
            .map(properties -> properties.rewrite(uri))
            .orElse(uri);
    }

    @Data
    public static class UriProperties implements UriCustomizer {

        private String host = "localhost";

        private int port = 80;

        @Override
        public URI rewrite(URI uri) {
            return fromUri(uri)
                    .host(host)
                    .port(port)
                    .build().toUri();
        }
    }
}
