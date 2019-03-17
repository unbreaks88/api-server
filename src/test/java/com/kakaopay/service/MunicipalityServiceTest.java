package com.kakaopay.service;

import com.kakaopay.dto.request.MunicipalityInfoRequest;
import com.kakaopay.dto.response.MunicipalityInfoResponse;
import com.kakaopay.dto.response.RegionInfoResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MunicipalityServiceTest {

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
    public void getMunicipalityListTest() {
        List<MunicipalityInfoResponse> responses = municipalityService.getMunicipalityList();
        assertThat(responses).isNotNull();
        assertThat(responses.size()).isEqualTo(98);
        for (MunicipalityInfoResponse response : responses) {
            assertThat(response).isNotNull();
        }
    }

    @Test
    public void findByRegionTest() {
        MunicipalityInfoResponse expected = new MunicipalityInfoResponse("강릉시", "강릉시 소재 중소기업으로서 강릉시장이 추천한 자", "운전", "추천금액 이내", "3%", "강릉시", "강릉지점", "강릉시 소재 영업점");
        MunicipalityInfoResponse successResponse = municipalityService.findByRegion("강릉시");
        assertThat(successResponse).isNotNull();

        assertThat(successResponse.getRegion()).isEqualTo(expected.getRegion());
        assertThat(successResponse.getTarget()).isEqualTo(expected.getTarget());
        assertThat(successResponse.getUsage()).isEqualTo(expected.getUsage());
        assertThat(successResponse.getLimit()).isEqualTo(expected.getLimit());
        assertThat(successResponse.getRate()).isEqualTo(expected.getRate());
        assertThat(successResponse.getInstitute()).isEqualTo(expected.getInstitute());
        assertThat(successResponse.getMgmt()).isEqualTo(expected.getMgmt());
        assertThat(successResponse.getReception()).isEqualTo(expected.getReception());

        MunicipalityInfoResponse failResponse = municipalityService.findByRegion("강르");
        assertThat(failResponse).isNull();
    }

    @Test
    public void updateMunicipalityInfoTest() {
        MunicipalityInfoResponse response = municipalityService.findByRegion("경주시");
        MunicipalityInfoResponse expectedResult = new MunicipalityInfoResponse("경주시", "경주시 소재 중소기업으로서 경주시장이 추천한 자", "운전", "5억원 이내", "3.00%", "경주시", "대구경북동부지역본부", "경주시 소재 영업점");

        assertThat(response).isNotNull();
        assertThat(response.getRegion()).isEqualTo(expectedResult.getRegion());
        assertThat(response.getTarget()).isEqualTo(expectedResult.getTarget());
        assertThat(response.getUsage()).isEqualTo(expectedResult.getUsage());
        assertThat(response.getLimit()).isEqualTo(expectedResult.getLimit());
        assertThat(response.getRate()).isEqualTo(expectedResult.getRate());
        assertThat(response.getInstitute()).isEqualTo(expectedResult.getInstitute());
        assertThat(response.getMgmt()).isEqualTo(expectedResult.getMgmt());
        assertThat(response.getReception()).isEqualTo(expectedResult.getReception());

        MunicipalityInfoRequest updateRequest = new MunicipalityInfoRequest("경주시", "경주시 소재 중소기업으로서 경주시장이 추천한 자", "운전", "5억원 이내", "5.00%", "경주시", "대구경북동부지역본부", "경주시 소재 영업점");
        MunicipalityInfoResponse updatedResponse = municipalityService.updateMunicipalityInfo("경주시", updateRequest);
        MunicipalityInfoResponse updatedExpectedResult = new MunicipalityInfoResponse("경주시", "경주시 소재 중소기업으로서 경주시장이 추천한 자", "운전", "5억원 이내", "5.00%", "경주시", "대구경북동부지역본부", "경주시 소재 영업점");

        assertThat(updatedResponse).isNotNull();
        assertThat(updatedResponse.getRegion()).isEqualTo(updatedExpectedResult.getRegion());
        assertThat(updatedResponse.getTarget()).isEqualTo(updatedExpectedResult.getTarget());
        assertThat(updatedResponse.getUsage()).isEqualTo(updatedExpectedResult.getUsage());
        assertThat(updatedResponse.getLimit()).isEqualTo(updatedExpectedResult.getLimit());
        assertThat(updatedResponse.getRate()).isEqualTo(updatedExpectedResult.getRate());
        assertThat(updatedResponse.getInstitute()).isEqualTo(updatedExpectedResult.getInstitute());
        assertThat(updatedResponse.getMgmt()).isEqualTo(updatedExpectedResult.getMgmt());
        assertThat(updatedResponse.getReception()).isEqualTo(updatedExpectedResult.getReception());
    }

    @Test
    public void orderByRateDescTest() {
        RegionInfoResponse response = municipalityService.orderByRateDesc(5);
        assertThat(response).isNotNull();
        assertThat(response.getRegion()).isEqualTo("경기도, 제주도, 국토교통부, 인천광역시, 안양시");
    }

    @Test
    public void getMinRateRegionTest() {
        RegionInfoResponse response = municipalityService.getMinRateRegion();
        assertThat(response).isNotNull();
        assertThat(response.getRegion()).isEqualTo("금천구");
    }
}