package io.spring.lab.store.basket;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.spring.lab.store.StubRunnerTestBase;
import io.spring.lab.store.basket.item.BasketItem;
import io.spring.lab.store.basket.item.BasketItemService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@SpringBootTest(webEnvironment = NONE)
public class BasketServiceTest extends StubRunnerTestBase {

    @Autowired
    BasketService baskets;

    @Autowired
    BasketItemService basketItems;

    @Test
    public void shouldUpdateBasketWithRegularPriceItem() {
        // given
        Basket basket = baskets.create();

        // when
        BasketUpdateDiff diff = baskets.updateItem(basket.getId(), ITEM_ID, ITEM_REGULAR_COUNT);

        // then
        assertThat(diff).isNotNull();
        assertThat(diff.getCountDiff()).isEqualTo(ITEM_REGULAR_COUNT);
        assertThat(diff.getPriceDiff()).isEqualByComparingTo(ITEM_REGULAR_PRICE);

        // and
        BasketItem basketItem = basketItems.findOneItem(basket.getId(), ITEM_ID);
        assertThat(basketItem.getName()).isEqualTo(ITEM_NAME);
        assertThat(basketItem.getTotalPrice()).isEqualByComparingTo(ITEM_REGULAR_PRICE);
        assertThat(basketItem.getSpecialId()).isNull();
    }

    @Test
    public void shouldUpdateBasketWithSpecialPriceItem() {
        // given
        Basket basket = baskets.create();

        // when
        BasketUpdateDiff diff = baskets.updateItem(basket.getId(), ITEM_ID, ITEM_SPECIAL_COUNT);

        // then
        assertThat(diff).isNotNull();
        assertThat(diff.getCountDiff()).isEqualTo(ITEM_SPECIAL_COUNT);
        assertThat(diff.getPriceDiff()).isEqualByComparingTo(ITEM_SPECIAL_PRICE);

        // and
        BasketItem basketItem = basketItems.findOneItem(basket.getId(), ITEM_ID);
        assertThat(basketItem.getName()).isEqualTo(ITEM_NAME);
        assertThat(basketItem.getTotalPrice()).isEqualByComparingTo(ITEM_SPECIAL_PRICE);
        assertThat(basketItem.getSpecialId()).isEqualTo(SPECIAL_ID);
    }
}
