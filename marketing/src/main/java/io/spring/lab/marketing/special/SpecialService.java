package io.spring.lab.marketing.special;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import io.spring.lab.marketing.special.calculate.SpecialCalculation;
import io.spring.lab.marketing.special.calculate.SpecialCalculator;
import io.spring.lab.marketing.special.select.SpecialSelector;
import io.spring.lab.math.MathProperties;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SpecialService {

	private final SpecialRepository specials;
	private final SpecialSelector selector;
	private final MathProperties math;

	public Special findOne(String id) {
		return specials.findOne(id);
	}

	public List<Special> findAll() {
		return specials.findAll();
	}

	public List<Special> findByItemId(long itemId) {
		return specials.findByItemId(itemId);
	}

	public Special create(Special special) {
		return specials.save(special);
	}

	public SpecialCalculation calculateFor(long itemId, BigDecimal unitPrice, int unitCount) {
		List<SpecialCalculator> candidates = specials.findByItemIdAndBatchSizeLessThanEqual(itemId, unitCount);
		return selector.selectSpecial(candidates, unitCount, unitPrice)
				.calculateFor(unitCount, unitPrice, math);
	}
}
