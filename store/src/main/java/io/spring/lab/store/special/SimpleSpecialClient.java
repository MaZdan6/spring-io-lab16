package io.spring.lab.store.special;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SimpleSpecialClient implements SpecialClient {

    private static final String BASE_URI = "http://marketing";
    private static final String SPECIAL_CALCULATION_URI = BASE_URI + "/specials/{itemId}/calculate";

    private final RestTemplate rest;

    @Override
    public SpecialCalculation calculateFor(long itemId, SpecialCalculationRequest request) {
        return rest.postForObject(SPECIAL_CALCULATION_URI, request, SpecialCalculation.class, itemId);
    }
}
