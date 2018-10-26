package io.spring.lab.store.item;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface ItemStreamsBinding {

    String CHECKOUT_ITEM = "checkoutItem";

    @Output(CHECKOUT_ITEM)
    MessageChannel checkoutItem();
}
