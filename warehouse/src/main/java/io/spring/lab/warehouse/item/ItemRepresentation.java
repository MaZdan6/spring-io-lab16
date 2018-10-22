package io.spring.lab.warehouse.item;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemRepresentation {

	private String name;

	@JsonProperty("count")
	private int stock;

	private BigDecimal price;

	static ItemRepresentation of(Item item) {
		return new ItemRepresentation(item.getName(), item.getCount(), item.getPrice());
	}

	Item asItem() {
		return new Item(null, name, stock, price);
	}
}
