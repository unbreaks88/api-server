package com.kakaopay.controller;

import com.kakaopay.service.MunicipalityService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MunicipalityControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MunicipalityService municipalityService;


    @Test
    public void getCoupon() throws Exception {
//        ResponseEntity<MunicipalityInfoEntity> response = restTemplate.getForEntity("/v1/municipality/list", MunicipalityInfoEntity.class);
//        log.info("getCoupon API: " + response);
    }
}