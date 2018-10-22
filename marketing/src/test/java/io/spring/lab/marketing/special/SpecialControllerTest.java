package io.spring.lab.marketing.special;

import java.math.BigDecimal;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.GET;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class SpecialControllerTest {

    @Autowired
    TestRestTemplate rest;

    @Test
    public void shouldListSpecials() {
        // given
        ParameterizedTypeReference<List<SpecialRepresentation>> specialsType =
                new ParameterizedTypeReference<List<SpecialRepresentation>>() { };

        // when
        ResponseEntity<List<SpecialRepresentation>> response =
                rest.exchange("/specials", GET, null /*no body*/, specialsType);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // and
        List<SpecialRepresentation> specials = response.getBody();
        assertThat(specials).hasSize(4);
        assertThat(specials).contains(new SpecialRepresentation(1L, 3, BigDecimal.valueOf(70.0)));
    }
}