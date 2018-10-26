package io.spring.lab.store.item;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import io.spring.lab.cloud.ConditionalOnFeignClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ConditionalOnFeignClient
@AllArgsConstructor
public class DeclarativeItemsClient implements ItemsClient {

    private final FeignItemsClient items;

    private final ItemStreamsBinding binding;

    @Override
    public ItemRepresentation findOne(long id) {
        ItemRepresentation representation = items.findOne(id);
        log.info("Declarative client got item from instance: {}", representation.getInstanceId());
        return representation;
    }

    @Override
    public void updateStock(ItemStockUpdate changes) {
        binding.checkoutItem().send(MessageBuilder.withPayload(changes).build());
    }

    @FeignClient(name = "warehouse", path = "/items")
    interface FeignItemsClient {

        @GetMapping("/{id}")
        ItemRepresentation findOne(@PathVariable("id") long id);
    }
}
