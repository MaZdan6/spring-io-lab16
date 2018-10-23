package io.spring.lab.web.client.request;

import java.net.URI;

public interface UriCustomizer {

    URI rewrite(URI uri);
}
