package com.kakaopay.controller;

import com.kakaopay.dto.request.MunicipalityInfoRequest;
import com.kakaopay.dto.response.MunicipalityInfoResponse;
import com.kakaopay.dto.response.StringResponse;
import com.kakaopay.service.MunicipalityService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MunicipalityControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private MunicipalityService municipalityService;

    private static boolean initilize = false;

    @Before
    public void setUp() throws Exception {
        if (!initilize) {
            String filePath = "src/test/resources/test_data.csv";
            FileInputStream fileInputStream = new FileInputStream(filePath);
            MultipartFile multipartFile = new MockMultipartFile("test_data.csv", fileInputStream);
            municipalityService.insertRows(multipartFile);
        }
        initilize = true;
    }


    @Test
    public void getMunicipalityInfoTest() {
        ResponseEntity<MunicipalityInfoResponse> response = testRestTemplate.getForEntity("/api/municipality/강릉시", MunicipalityInfoResponse.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody().get
    }

    @Test
    public void getMunicipalityListTest() {
        ResponseEntity<List<MunicipalityInfoResponse>> response = testRestTemplate.exchange(
                "/api/municipality",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<MunicipalityInfoResponse>>() {
                });

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().size()).isEqualTo(98);
    }

//    @Test
//    public void updateMunicipality() {
//        MunicipalityInfoRequest modifyRequest = new MunicipalityInfoRequest("강릉시", "강릉시 소재 중소기업으로서 강릉시장이 추천한 자", "운전", "추천금액 이내", "3%", "강릉시", "강릉지점", "강릉시 소재 영업점");
//        MunicipalityInfoResponse modifiedRespose = new MunicipalityInfoResponse("강릉시", "강릉시 소재 중소기업으로서 강릉시장이 추천한 자", "운전", "추천금액 이내", "4%", "강릉시", "강릉지점", "강릉시 소재 영업점");
//
//        HttpHeaders headers = new HttpHeaders();
////        headers.setContentType();
//
//        HttpEntity<MunicipalityInfoRequest> testRequest = new HttpEntity(modifyRequest, headers);
//        ResponseEntity<MunicipalityInfoResponse> response = testRestTemplate.exchange(
//                "/api/municipality/제주시",
//                HttpMethod.PUT,
//                testRequest,
//                new ParameterizedTypeReference<MunicipalityInfoResponse>(){});
//
////        ResponseEntity<MunicipalityInfoResponse> response = testRestTemplate.put("/api/municipality/제주시", modifyRequest, MunicipalityInfoResponse.class);
//        assertThat(response).isNotNull();
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//    }

    @Test
    public void orderByRateDesc() {
        ResponseEntity<List<String>> response = testRestTemplate.exchange(
                "/api/municipality/5",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<String>>() {
                });

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().size()).isEqualTo(5);

    }

    @Test
    public void recommendMunicipality() {
        ResponseEntity<StringResponse> response = testRestTemplate.exchange(
                "/api/municipality/recommend",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<StringResponse>() {
                });

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody().size()).isEqualTo(5);
    }
}
