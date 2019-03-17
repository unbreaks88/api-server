package com.kakaopay.service;

import com.kakaopay.dto.request.MunicipalityInfoRequest;
import com.kakaopay.dto.response.MunicipalityInfoResponse;
import com.kakaopay.dto.response.RegionInfoResponse;
import com.kakaopay.repository.MunicipalityRepository;
import com.kakaopay.repository.SupportMunicipalityRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
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

    @Autowired
    private MunicipalityRepository municipalityRepository;

    @Before
    public void setUp() throws Exception {
        String filePath = "src/test/resources/test_data.csv";
        FileInputStream fileInputStream = new FileInputStream(filePath);
        MultipartFile multipartFile = new MockMultipartFile("test_data.csv", fileInputStream);
        municipalityService.insertRows(multipartFile);
    }

    @After
    public void clean() {
        municipalityRepository.deleteAll();
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
        MunicipalityInfoResponse originResponse = municipalityService.findByRegion("경주시");
        MunicipalityInfoResponse originExpectedResponse = new MunicipalityInfoResponse("경주시", "경주시 소재 중소기업으로서 경주시장이 추천한 자", "운전", "5억원 이내", "3.00%", "경주시", "대구경북동부지역본부", "경주시 소재 영업점");

        assertThat(originResponse).isNotNull();
        assertThat(originResponse.getRegion()).isEqualTo(originExpectedResponse.getRegion());
        assertThat(originResponse.getTarget()).isEqualTo(originExpectedResponse.getTarget());
        assertThat(originResponse.getUsage()).isEqualTo(originExpectedResponse.getUsage());
        assertThat(originResponse.getLimit()).isEqualTo(originExpectedResponse.getLimit());
        assertThat(originResponse.getRate()).isEqualTo(originExpectedResponse.getRate());
        assertThat(originResponse.getInstitute()).isEqualTo(originExpectedResponse.getInstitute());
        assertThat(originResponse.getMgmt()).isEqualTo(originExpectedResponse.getMgmt());
        assertThat(originResponse.getReception()).isEqualTo(originExpectedResponse.getReception());

        MunicipalityInfoRequest updateRequest = new MunicipalityInfoRequest("경주시", "경주시 소재 중소기업으로서 경주시장이 추천한 자", "운전", "5억원 이내", "5.00%", "경주시", "대구경북동부지역본부", "경주시 소재 영업점");
        MunicipalityInfoResponse updatedResponse = municipalityService.updateMunicipalityInfo("경주시", updateRequest);
        MunicipalityInfoResponse updatedExpectedResponse = new MunicipalityInfoResponse("경주시", "경주시 소재 중소기업으로서 경주시장이 추천한 자", "운전", "5억원 이내", "5.00%", "경주시", "대구경북동부지역본부", "경주시 소재 영업점");

        assertThat(updatedResponse).isNotNull();
        assertThat(updatedResponse.getRegion()).isEqualTo(updatedExpectedResponse.getRegion());
        assertThat(updatedResponse.getTarget()).isEqualTo(updatedExpectedResponse.getTarget());
        assertThat(updatedResponse.getUsage()).isEqualTo(updatedExpectedResponse.getUsage());
        assertThat(updatedResponse.getLimit()).isEqualTo(updatedExpectedResponse.getLimit());
        assertThat(updatedResponse.getRate()).isEqualTo(updatedExpectedResponse.getRate());
        assertThat(updatedResponse.getInstitute()).isEqualTo(updatedExpectedResponse.getInstitute());
        assertThat(updatedResponse.getMgmt()).isEqualTo(updatedExpectedResponse.getMgmt());
        assertThat(updatedResponse.getReception()).isEqualTo(updatedExpectedResponse.getReception());
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