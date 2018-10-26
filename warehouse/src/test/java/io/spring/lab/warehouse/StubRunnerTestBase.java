package io.spring.lab.warehouse;

import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;

@AutoConfigureStubRunner( stubsMode = StubRunnerProperties.StubsMode.LOCAL, ids = {
        "io.spring.lab:store:+"
})
public abstract class StubRunnerTestBase extends SpringTestBase {

    protected static final long ITEM_ID = 1L;
    protected static final int ITEM_REGULAR_COUNT = 2;

}