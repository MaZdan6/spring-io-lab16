package io.spring.lab.warehouse;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.spring.lab.warehouse.error.DefaultErrorController;
import io.spring.lab.warehouse.item.Item;
import io.spring.lab.warehouse.item.ItemController;
import io.spring.lab.warehouse.item.ItemService;

import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {
        DefaultErrorController.class,
        ItemController.class
})
public abstract class WarehouseContractContextTestBase {

    @MockBean
    ItemService items;

    @Autowired
    MockMvc mvc;

    @Before
    public void setUp() {
        RestAssuredMockMvc.mockMvc(mvc);

        doReturn(itemA())
                .when(items).findOne(1L);
    }

    private Item itemA() {
        return new Item(1L, "A", 100, BigDecimal.valueOf(40.0));
    }
}
