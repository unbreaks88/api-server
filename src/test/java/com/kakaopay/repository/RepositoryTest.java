package com.kakaopay.repository;

import com.kakaopay.model.MunicipalityInfoEntity;
import com.kakaopay.model.SupportMunicipalityInfoEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RepositoryTest {

    @Autowired
    private MunicipalityRepository municipalityRepository;

    @Autowired
    private SupportMunicipalityRepository supportMunicipalityRepository;

    @Before
    public void setup() {
        final List<MunicipalityInfoEntity> savedEntities = Arrays.asList(
                new MunicipalityInfoEntity(new SupportMunicipalityInfoEntity("NC1", "강릉시"), "강릉시 소재 중소기업으로서 강릉시장이 추천한 자", "운전", "추천금액 이내", "3%", "강릉시", "강릉지점", "강릉시 소재 영업점"),
                new MunicipalityInfoEntity(new SupportMunicipalityInfoEntity("NC2", "경기도"), "경기도 소재 중소기업으로서 경기도지사가 추천한 자", "운전 및 시", "300억원 이내", "0.3%~2.0%", "경기신용보증재단", "경수지역본부", "전 영업점"),
                new MunicipalityInfoEntity(new SupportMunicipalityInfoEntity("NC3", "경주시"), "경주시 소재 중소기업으로서 경주시장이 추천한 자", "운전", "5억원 이내내", "3.00%", "경주시", "대구경북동부지역본부", "경주시 소재 영업점"),
                new MunicipalityInfoEntity(new SupportMunicipalityInfoEntity("NC4", "김포시"), "김포시 소재 중소기업(소상공인 포함)으로서 김포시장이 추천한 자", "운전", "2억원 이내", "1.50%~2.0%", "김포시", "김포지점", "김포시 관내 영업점"),
                new MunicipalityInfoEntity(new SupportMunicipalityInfoEntity("NC5", "김해시"), "김해시 소재 중소기업(소상공인 포함)으로서 김해시장이 추천한 자", "운전 및 시설", "3억원 이내", "2.0%~2.5%", "김해시, 경남신용보증재단 김해지점", "김해지점", "전 영업점"),
                new MunicipalityInfoEntity(new SupportMunicipalityInfoEntity("NC6", "금천구"), "금천구 소재 중소기업으로서 금천구청장이 추천한 자", "운전", "2억원 이내", "2.0%~2.5%", "금천구", "가산디지털지점", "전 영업점")
        );
        municipalityRepository.saveAll(savedEntities);
        assertThat(municipalityRepository.findAll(), is(savedEntities));
    }

    @Test
    public void findByRegion() {
        SupportMunicipalityInfoEntity sExpected1 = supportMunicipalityRepository.findByRegion("강릉시");
        MunicipalityInfoEntity expected1 = municipalityRepository.findBySupportInfoEntity(sExpected1);

        SupportMunicipalityInfoEntity sExpected2 = supportMunicipalityRepository.findByRegion("경기도");
        MunicipalityInfoEntity expected2 = municipalityRepository.findBySupportInfoEntity(sExpected2);

        SupportMunicipalityInfoEntity sExpected3 = supportMunicipalityRepository.findByRegion("경주시");
        MunicipalityInfoEntity expected3 = municipalityRepository.findBySupportInfoEntity(sExpected3);

        SupportMunicipalityInfoEntity sExpected4 = supportMunicipalityRepository.findByRegion("김포시");
        MunicipalityInfoEntity expected4 = municipalityRepository.findBySupportInfoEntity(sExpected4);

        SupportMunicipalityInfoEntity sExpected5 = supportMunicipalityRepository.findByRegion("김해시");
        MunicipalityInfoEntity expected5 = municipalityRepository.findBySupportInfoEntity(sExpected5);

        SupportMunicipalityInfoEntity sExpected6 = supportMunicipalityRepository.findByRegion("금천구");
        MunicipalityInfoEntity expected6 = municipalityRepository.findBySupportInfoEntity(sExpected6);

        SupportMunicipalityInfoEntity sExpected7 = supportMunicipalityRepository.findByRegion("제주시");
        MunicipalityInfoEntity expected7 = municipalityRepository.findBySupportInfoEntity(sExpected7);

        assertThat(sExpected1, is(notNullValue()));
        assertThat(sExpected2, is(notNullValue()));
        assertThat(sExpected3, is(notNullValue()));
        assertThat(sExpected4, is(notNullValue()));
        assertThat(sExpected5, is(notNullValue()));
        assertThat(sExpected6, is(notNullValue()));
        assertThat(sExpected7, is(nullValue()));

        assertThat(expected1, is(notNullValue()));
        assertThat(expected2, is(notNullValue()));
        assertThat(expected3, is(notNullValue()));
        assertThat(expected4, is(notNullValue()));
        assertThat(expected5, is(notNullValue()));
        assertThat(expected6, is(notNullValue()));
        assertThat(expected7, is(nullValue()));
    }
}

