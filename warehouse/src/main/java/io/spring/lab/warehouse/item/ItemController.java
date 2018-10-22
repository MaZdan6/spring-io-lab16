package io.spring.lab.warehouse.item;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.util.stream.Collectors.toList;

@Slf4j
@RestController
@RequestMapping("/items")
@AllArgsConstructor
public class ItemController {

    private final ItemService items;

    @GetMapping
    List<ItemRepresentation> findAll() {
        return items.findAll().stream()
                .map(ItemRepresentation::of)
                .collect(toList());
    }

    @GetMapping("/{id}")
    public ItemRepresentation findOne(@PathVariable("id") long id) {
        return ItemRepresentation.of(items.findOne(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ItemRepresentation request) {
        Item item = items.create(request.asItem());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ItemRepresentation update(@PathVariable("id") long id, @RequestBody ItemUpdate changes) {
        return ItemRepresentation.of(items.update(changes.withId(id)));
    }

}
