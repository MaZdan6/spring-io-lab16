package io.spring.lab.web.client.request;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
@ConditionalOnClass(HttpHeaders.class)
@ConditionalOnProperty(name = "uri.customizer.enabled", havingValue = "true", matchIfMissing = true)
@ConditionalOnMissingBean(UriCustomizer.class)
@EnableConfigurationProperties(UriCustomizerProperties.class)
public class UriCustomizerAutoConfiguration {

}
