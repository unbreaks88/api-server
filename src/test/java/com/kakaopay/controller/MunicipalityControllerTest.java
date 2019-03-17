package com.kakaopay.controller;

import com.kakaopay.dto.request.MunicipalityInfoRequest;
import com.kakaopay.dto.response.RegionInfoResponse;
import com.kakaopay.dto.response.MunicipalityInfoResponse;
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
        MunicipalityInfoResponse expectedResponse = new MunicipalityInfoResponse("강릉시", "강릉시 소재 중소기업으로서 강릉시장이 추천한 자", "운전", "추천금액 이내", "3%", "강릉시", "강릉지점", "강릉시 소재 영업점");
        ResponseEntity<MunicipalityInfoResponse> response = testRestTemplate.getForEntity("/api/municipality/강릉시", MunicipalityInfoResponse.class);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getRegion()).isEqualTo(expectedResponse.getRegion());
        assertThat(response.getBody().getTarget()).isEqualTo(expectedResponse.getTarget());
        assertThat(response.getBody().getUsage()).isEqualTo(expectedResponse.getUsage());
        assertThat(response.getBody().getLimit()).isEqualTo(expectedResponse.getLimit());
        assertThat(response.getBody().getRate()).isEqualTo(expectedResponse.getRate());
        assertThat(response.getBody().getInstitute()).isEqualTo(expectedResponse.getInstitute());
        assertThat(response.getBody().getMgmt()).isEqualTo(expectedResponse.getMgmt());
        assertThat(response.getBody().getReception()).isEqualTo(expectedResponse.getReception());
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

    @Test
    public void updateMunicipalityTest() {
        MunicipalityInfoResponse originExpectedResponse = new MunicipalityInfoResponse("강릉시", "강릉시 소재 중소기업으로서 강릉시장이 추천한 자", "운전", "추천금액 이내", "3%", "강릉시", "강릉지점", "강릉시 소재 영업점");
        ResponseEntity<MunicipalityInfoResponse> originResponse = testRestTemplate.getForEntity("/api/municipality/강릉시", MunicipalityInfoResponse.class);

        assertThat(originResponse).isNotNull();
        assertThat(originResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(originResponse.getBody().getRegion()).isEqualTo(originExpectedResponse.getRegion());
        assertThat(originResponse.getBody().getTarget()).isEqualTo(originExpectedResponse.getTarget());
        assertThat(originResponse.getBody().getUsage()).isEqualTo(originExpectedResponse.getUsage());
        assertThat(originResponse.getBody().getLimit()).isEqualTo(originExpectedResponse.getLimit());
        assertThat(originResponse.getBody().getRate()).isEqualTo(originExpectedResponse.getRate());
        assertThat(originResponse.getBody().getInstitute()).isEqualTo(originExpectedResponse.getInstitute());
        assertThat(originResponse.getBody().getMgmt()).isEqualTo(originExpectedResponse.getMgmt());
        assertThat(originResponse.getBody().getReception()).isEqualTo(originExpectedResponse.getReception());

        MunicipalityInfoRequest modifyRequest = new MunicipalityInfoRequest("강릉시", "강릉시 소재 중소기업으로서 강릉시장이 추천한 자", "운전", "추천금액 이내", "4%", "강릉시", "강릉지점", "강릉시 소재 영업점");
        MunicipalityInfoResponse modifiedExpectedResponse = new MunicipalityInfoResponse("강릉시", "강릉시 소재 중소기업으로서 강릉시장이 추천한 자", "운전", "추천금액 이내", "4%", "강릉시", "강릉지점", "강릉시 소재 영업점");

        testRestTemplate.put("/api/municipality/강릉시", modifyRequest);
        ResponseEntity<MunicipalityInfoResponse> modifiedResponse = testRestTemplate.getForEntity("/api/municipality/강릉시", MunicipalityInfoResponse.class);

        assertThat(modifiedResponse).isNotNull();
        assertThat(modifiedResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(modifiedResponse.getBody().getRegion()).isEqualTo(modifiedExpectedResponse.getRegion());
        assertThat(modifiedResponse.getBody().getTarget()).isEqualTo(modifiedExpectedResponse.getTarget());
        assertThat(modifiedResponse.getBody().getUsage()).isEqualTo(modifiedExpectedResponse.getUsage());
        assertThat(modifiedResponse.getBody().getLimit()).isEqualTo(modifiedExpectedResponse.getLimit());
        assertThat(modifiedResponse.getBody().getRate()).isEqualTo(modifiedExpectedResponse.getRate());
        assertThat(modifiedResponse.getBody().getInstitute()).isEqualTo(modifiedExpectedResponse.getInstitute());
        assertThat(modifiedResponse.getBody().getMgmt()).isEqualTo(modifiedExpectedResponse.getMgmt());
        assertThat(modifiedResponse.getBody().getReception()).isEqualTo(modifiedExpectedResponse.getReception());
    }

    @Test
    public void orderByRateDescTest() {
        ResponseEntity<RegionInfoResponse> region = testRestTemplate.getForEntity("/api/municipality/sort/5", RegionInfoResponse.class);

        assertThat(region).isNotNull();
        assertThat(region.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(region.getBody().getRegion()).isEqualTo("경기도, 제주도, 국토교통부, 인천광역시, 안양시");
    }

    @Test
    public void recommendMunicipalityTest() {
        ResponseEntity<RegionInfoResponse> response = testRestTemplate.getForEntity("/api/municipality/recommend", RegionInfoResponse.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getRegion()).isEqualTo("금천구");
    }
}
