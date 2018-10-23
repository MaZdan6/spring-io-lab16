package io.spring.lab.store;

import java.math.BigDecimal;

import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;

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

}
