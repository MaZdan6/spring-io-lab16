package io.spring.lab.store.special;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.spring.lab.cloud.ConditionalOnFeignClient;
import io.spring.lab.math.MathProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;

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

    @FeignClient(name = "marketing", path = "/specials", fallback = FallbackSpecialClient.class)
    interface FeignSpecialClient {

        @PostMapping("/{itemId}/calculate")
        SpecialCalculation calculateFor(
                @PathVariable("itemId") long itemId,
                @RequestBody SpecialCalculationRequest request);
    }

    @Component
    @ConditionalOnFeignClient
    @AllArgsConstructor
    static class FallbackSpecialClient implements FeignSpecialClient {

        private MathProperties math;

        @Override // 10% discount as fallback
        public SpecialCalculation calculateFor(long itemId, SpecialCalculationRequest request) {
            BigDecimal unitCount = math.bigDecimal(request.getUnitCount());
            BigDecimal regularPrice = request.getUnitPrice().multiply(unitCount, math.getContext());
            BigDecimal discountRate = ONE.divide(TEN, math.getContext());
            BigDecimal discount = regularPrice.multiply(discountRate, math.getContext());
            BigDecimal fallbackPrice = regularPrice.subtract(discount, math.getContext());
            return new SpecialCalculation("fallback", fallbackPrice);
        }
    }
}
