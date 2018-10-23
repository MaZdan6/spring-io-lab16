package io.spring.lab.store;

import java.math.BigDecimal;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.contract.stubrunner.StubFinder;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

import io.spring.lab.web.client.request.UriCustomizer;

import static java.util.Optional.ofNullable;
import static org.springframework.web.util.UriComponentsBuilder.fromUri;

@ContextConfiguration(classes = StubRunnerTestBase.StubRunnerBaseConfig.class)
@AutoConfigureStubRunner(stubsMode = StubRunnerProperties.StubsMode.LOCAL, ids = {
        "io.spring.lab:warehouse:+",
        "io.spring.lab:marketing:+"
})
public abstract class StubRunnerTestBase extends SpringTestBase {

    protected static final long ITEM_ID = 1L;
    protected static final String ITEM_NAME = "A";
    protected static final int ITEM_REGULAR_COUNT = 2;
    protected static final BigDecimal ITEM_REGULAR_PRICE = BigDecimal.valueOf(80.0);
    protected static final int ITEM_SPECIAL_COUNT = 5;
    protected static final BigDecimal ITEM_SPECIAL_PRICE = BigDecimal.valueOf(150.0);
    protected static final String SPECIAL_ID = "abc";

    @TestConfiguration
    static class StubRunnerBaseConfig {

        @Bean
        UriCustomizer stubRunnerUriCustomizer(StubFinder stubFinder) {
            return uri -> ofNullable(stubFinder.findAllRunningStubs().getPort(uri.getHost()))
                    .map(port -> fromUri(uri)
                            .host("localhost")
                            .port(port)
                            .build().toUri())
                    .orElse(uri);
        }
    }
}
