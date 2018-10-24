package io.spring.lab.marketing;

import java.math.BigDecimal;

import org.junit.Before;
import org.springframework.test.web.servlet.MockMvc;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.spring.lab.marketing.special.SpecialController;
import io.spring.lab.marketing.special.SpecialService;
import io.spring.lab.marketing.special.calculate.SpecialCalculation;

import static org.mockito.AdditionalMatchers.cmpEq;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public abstract class MarketingContractTestBase {

    private static final long ITEM_ID = 1L;
    private static final BigDecimal UNIT_PRICE = BigDecimal.valueOf(40);

    private static final int SPECIAL_UNIT_COUNT = 5;
    private static final String SPECIAL_ID = "abc";
    private static final BigDecimal SPECIAL_TOTAL_PRICE = BigDecimal.valueOf(150);

    private static final int REGULAR_UNIT_COUNT = 2;
    private static final BigDecimal REGULAR_TOTAL_PRICE = BigDecimal.valueOf(80);

    @Before
    public void setUp() {
        MockMvc mvc = standaloneSetup(prepareSpecialController()).build();
        RestAssuredMockMvc.mockMvc(mvc);
    }

    private SpecialController prepareSpecialController() {
        SpecialService specials = mock(SpecialService.class);

        doReturn(specialPrice())
                .when(specials).calculateFor(eq(ITEM_ID), cmpEq(UNIT_PRICE), eq(SPECIAL_UNIT_COUNT));
        doReturn(regularPrice())
                .when(specials).calculateFor(eq(ITEM_ID), cmpEq(UNIT_PRICE), eq(REGULAR_UNIT_COUNT));

        return new SpecialController(specials);
    }

    private SpecialCalculation specialPrice() {
        return new SpecialCalculation(SPECIAL_ID, SPECIAL_TOTAL_PRICE);
    }

    private SpecialCalculation regularPrice() {
        return new SpecialCalculation(null, REGULAR_TOTAL_PRICE);
    }
}
