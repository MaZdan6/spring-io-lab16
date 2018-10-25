package io.spring.lab.store.special;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.spring.lab.cloud.ConditionalOnFeignClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ConditionalOnFeignClient
@AllArgsConstructor
public class DeclarativeSpecialClient implements SpecialClient {

    private final FeignSpecialClient specials;

    @Override
    public SpecialCalculation calculateFor(long itemId, SpecialCalculationRequest request) {
        SpecialCalculation calculation = specials.calculateFor(itemId, request);
        log.info("Declarative client got special calculation: {} ({})",
                calculation.getTotalPrice(),
                Optional.ofNullable(calculation.getSpecialId()).orElse("regular"));
        return calculation;
    }

    @FeignClient(name = "marketing", path = "/specials")
    interface FeignSpecialClient {

        @PostMapping("/{itemId}/calculate")
        SpecialCalculation calculateFor(
                @PathVariable("itemId") long itemId,
                @RequestBody SpecialCalculationRequest request);
    }
}
