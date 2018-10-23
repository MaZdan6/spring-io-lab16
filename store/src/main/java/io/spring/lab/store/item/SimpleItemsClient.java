package io.spring.lab.store.item;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.AllArgsConstructor;

import static java.util.Collections.singletonList;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

@Component
@AllArgsConstructor
public class SimpleItemsClient implements ItemsClient {

    private static final String BASE_URI = "http://warehouse";
    private static final String ITEMS_URI = BASE_URI + "/items";
    private static final String ITEM_URI = BASE_URI + "/items/{id}";
    private static final String ITEM_STOCK_URI = BASE_URI + "/items/{id}/stock";

    private final RestTemplate rest;

    @Override
    public List<ItemRepresentation> findAll() {
        ParameterizedTypeReference<List<ItemRepresentation>> type =
                new ParameterizedTypeReference<List<ItemRepresentation>>() {};
        return rest.exchange(ITEMS_URI, HttpMethod.GET, emptyEntity(), type).getBody();
    }

    @Override
    public ItemRepresentation findOne(long id) {
        return rest.exchange(ITEM_URI, HttpMethod.GET, emptyEntity(), ItemRepresentation.class, id).getBody();
    }

    @Override
    public void updateStock(ItemStockUpdate changes) {
        rest.put(ITEM_STOCK_URI, changes, changes.getId());
    }

    private HttpEntity<?> emptyEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(singletonList(APPLICATION_JSON_UTF8));
        return new HttpEntity<>(headers);
    }
}
