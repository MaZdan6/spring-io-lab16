package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, properties = {
        "greeting.template=Hello %s"
})
public class GreetingControllerTest {

    @Autowired
    TestRestTemplate rest;

    @Test
    public void shouldSayHello() {
        // when
        Greeting greeting = rest.getForObject("/greet/test", Greeting.class);

        // then
        assertThat(greeting.getMessage()).isEqualTo("Hello test");
    }
}
