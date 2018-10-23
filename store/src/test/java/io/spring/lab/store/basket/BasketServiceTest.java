package io.spring.lab.store.basket;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import io.spring.lab.store.StubRunnerTestBase;
import io.spring.lab.store.basket.item.BasketItemService;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@SpringBootTest(webEnvironment = NONE)
@ActiveProfiles("rewrite")
public class BasketServiceTest extends StubRunnerTestBase {

    @Autowired
    BasketService baskets;

    @Autowired
    BasketItemService basketItems;

    @Test
    public void shouldUpdateBasketWithRegularPriceItem() {

    }

    @Test
    public void shouldUpdateBasketWithSpecialPriceItem() {

    }
}
