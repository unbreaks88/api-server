package com.kakaopay.service;

import com.kakaopay.model.MunicipalityInfoEntity;
import com.kakaopay.repository.MunicipalityRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MunicipalityServiceTest2 {


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


//    //    private MunicipalityService service = mock(MunicipalityService.class);
////    private MunicipalityRepository municipalityRepository = mock(MunicipalityRepository.class);z
//    private final List savedEntities = new ArrayList();
//
//    @Before
//    public void setUp() {
//        savedEntities.add(new MunicipalityInfoEntity("강릉시", "강릉시 소재 중소기업으로서 강릉시장이 추천한 자", "운전", "추천금액 이내", "3%", "강릉시", "강릉지점", "강릉시 소재 영업점"));
//        savedEntities.add(new MunicipalityInfoEntity("경기도", "경기도 소재 중소기업으로서 경기도지사가 추천한 자", "운전 및 시", "300억원 이내", "0.3%~2.0%", "경기신용보증재단", "경수지역본부", "전 영업점"));
//        savedEntities.add(new MunicipalityInfoEntity("경주시", "경주시 소재 중소기업으로서 경주시장이 추천한 자", "운전", "5억원 이내내", "3.00%", "경주시", "대구경북동부지역본부", "경주시 소재 영업점"));
//        savedEntities.add(new MunicipalityInfoEntity("김포시", "김포시 소재 중소기업(소상공인 포함)으로서 김포시장이 추천한 자", "운전", "2억원 이내", "1.50%~2.0%", "김포시", "김포지점", "김포시 관내 영업점"));
//        savedEntities.add(new MunicipalityInfoEntity("김해시", "김해시 소재 중소기업(소상공인 포함)으로서 김해시장이 추천한 자", "운전 및 시설", "3억원 이내", "2.0%~2.5%", "김해시, 경남신용보증재단 김해지점", "김해지점", "전 영업점"));
//        savedEntities.add(new MunicipalityInfoEntity("금천구", "금천구 소재 중소기업으로서 금천구청장이 추천한 자", "운전", "2억원 이내", "2.0%~2.5%", "금천구", "가산디지털지점", "전 영업점"));
//
//        municipalityRepository.saveAll(savedEntities);
//        assertThat(municipalityRepository.findAll(), is(savedEntities));
//    }
}