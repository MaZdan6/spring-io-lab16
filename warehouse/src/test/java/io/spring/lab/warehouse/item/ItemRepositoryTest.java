package io.spring.lab.warehouse.item;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.stereotype.Repository;

import io.spring.lab.warehouse.SpringTestBase;
import io.spring.lab.warehouse.WarehousePersistenceConfig;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.context.annotation.FilterType.ANNOTATION;
import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;

@DataJpaTest(includeFilters = {
        @Filter(type = ASSIGNABLE_TYPE, classes = WarehousePersistenceConfig.class),
        @Filter(type = ANNOTATION, classes = Repository.class)
})
@AutoConfigureTestDatabase
public class ItemRepositoryTest extends SpringTestBase {

    @Autowired
    ItemRepository items;

    @Autowired
    TestEntityManager jpa;

    @Test
    public void shouldFindGivenItemJPA() {
        jpa.persistAndFlush(new Item(null, "A", 100, BigDecimal.valueOf(40.0)));
        jpa.clear();

        Optional<Item> item = Optional.ofNullable(jpa.find(Item.class, 5L));

        assertThat(item).isPresent();
    }

    @Test
    public void shouldFindGivenItem() {
        Optional<Item> item = items.findOne(1L);

        assertThat(item).isPresent();
    }
}
