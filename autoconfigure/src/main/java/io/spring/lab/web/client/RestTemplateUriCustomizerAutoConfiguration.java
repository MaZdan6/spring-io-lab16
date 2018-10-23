package io.spring.lab.web.client;

import java.util.Collections;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;

import io.spring.lab.web.client.request.UriCustomizer;
import io.spring.lab.web.client.request.UriCustomizerAutoConfiguration;

@ConditionalOnClass(RestTemplateCustomizer.class)
@AutoConfigureAfter(UriCustomizerAutoConfiguration.class)
public class RestTemplateUriCustomizerAutoConfiguration {

    @Bean
    @ConditionalOnBean(UriCustomizer.class)
    RestTemplateCustomizer uriRewritingCustomizer(UriCustomizer customizer) {
        return restTemplate -> Collections.addAll(restTemplate.getInterceptors(), new UriRewritingInterceptor(customizer));
    }
}
