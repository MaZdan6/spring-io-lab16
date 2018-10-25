package io.spring.lab.store.item;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.spring.lab.cloud.ConditionalOnFeignClient;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ConditionalOnFeignClient
@AllArgsConstructor
public class DeclarativeItemsClient implements ItemsClient {

    private final FeignItemsClient items;

    @Override
    public ItemRepresentation findOne(long id) {
        ItemRepresentation representation = items.findOne(id);
        log.info("Declarative client got item from instance: {}", representation.getInstanceId());
        return representation;
    }

    @Override
    public void updateStock(ItemStockUpdate changes) {
        items.updateStock(changes.getId(), new FeignItemStockUpdate(changes.getCountDiff()));
    }

    @FeignClient(name = "warehouse", path = "/items")
    interface FeignItemsClient {

        @GetMapping("/{id}")
        ItemRepresentation findOne(@PathVariable("id") long id);

        @PutMapping("/{id}/stock")
        ItemRepresentation updateStock(@PathVariable("id") long id, @RequestBody FeignItemStockUpdate changes);
    }

    @Value
    class FeignItemStockUpdate {
        private int countDiff;
    }
}
