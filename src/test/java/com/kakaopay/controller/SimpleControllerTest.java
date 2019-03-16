package com.kakaopay.controller;

import com.kakaopay.dto.response.MunicipalityInfoResponse;
import lombok.extern.slf4j.Slf4j;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class SimpleControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void test() {
        ResponseEntity<String> response = testRestTemplate.getForEntity("/api/hello", String.class);
        log.info("getCoupon API: " + response);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().equalsIgnoreCase("hello"));
    }

    @Test
    public void test2() {
        ResponseEntity<List<MunicipalityInfoResponse>> response = testRestTemplate.exchange(
                "/api/municipality",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<MunicipalityInfoResponse>>() {
                });

        log.info("getCoupon API: " + response);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody().equalsIgnoreCase("hello"));
    }
}