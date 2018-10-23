package io.spring.lab.web.client;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import io.spring.lab.web.client.request.UriCustomizer;
import io.spring.lab.web.client.request.UriRewritingHttpRequestWrapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UriRewritingInterceptor implements ClientHttpRequestInterceptor {

    private final UriCustomizer customizer;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        return execution.execute(new UriRewritingHttpRequestWrapper(request, customizer), body);
    }
}
