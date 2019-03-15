package com.kakaopay.service;

import com.kakaopay.dto.response.MunicipalityInfoResponse;
import com.kakaopay.dto.response.StringResponse;
import com.kakaopay.model.MunicipalityInfoEntity;
import com.kakaopay.repository.MunicipalityRepository;
import com.kakaopay.util.Utils;
import com.kakaopay.vo.RecommendMunicipality;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MunicipalityService {

    @Autowired
    private MunicipalityRepository municipalityRepository;

    // FIXME Bulk insert 고민
    public void insertRows(List<CSVRecord> records) {
        List<MunicipalityInfoEntity> recordList = new ArrayList<>();
        for (CSVRecord record : records) {
            recordList.add(new MunicipalityInfoEntity(record.get("지자체명(기관명)"), record.get("지원대상"), record.get("용도"), record.get("지원한도"), record.get("이차보전"), record.get("추천기관"), record.get("관리점"), record.get("취급점")));
        }
        municipalityRepository.saveAll(recordList);
    }

    public List<MunicipalityInfoResponse> getMunicipalityList() {
        List<MunicipalityInfoResponse> responses = municipalityRepository.findAll()
                .stream()
                .map(entity -> {
                    return new MunicipalityInfoResponse(
                            entity.getRegion(), entity.getTarget(), entity.getUsage(), entity.getLimit(), entity.getRate(), entity.getInstitute(), entity.getMgmt(), entity.getReception()
                    );
                }).collect(Collectors.toList());
        return responses;
    }

    public MunicipalityInfoResponse findByRegion(final String region) {
        MunicipalityInfoEntity entity = municipalityRepository.findByRegion(region);
        return new MunicipalityInfoResponse(entity.getRegion(), entity.getTarget(), entity.getUsage(), entity.getLimit(), entity.getRate(), entity.getInstitute(), entity.getMgmt(), entity.getReception());
    }

    public MunicipalityInfoEntity updateMunicipalityInfo(final String region, MunicipalityInfoEntity entity) {
        MunicipalityInfoEntity updatedEntity = municipalityRepository.findByRegion(region);

        updatedEntity.setRegion(entity.getRegion());
        updatedEntity.setTarget(entity.getTarget());
        updatedEntity.setUsage(entity.getUsage());
        updatedEntity.setLimit(entity.getLimit());
        updatedEntity.setRate(entity.getRate());
        updatedEntity.setMgmt(entity.getMgmt());
        updatedEntity.setReception(entity.getReception());

        municipalityRepository.save(updatedEntity);
        return updatedEntity;
    }

    public List<String> orderByRateDesc(final int count) {
        final List<MunicipalityInfoEntity> entityList = municipalityRepository.findAll();

        /**
         * 1. 전체 entity에서 필요 정보만 추출(지자체명(기관명), 지원한도, 이차보전)
         * 2. 1차 정렬(지원한도, 내림차순)
         * 3. 2차 정렬(이차보전, 내림차순)
         * 4. topN개 추출
         */
        final List<String> sortedRegionList = entityList.stream().map(entity -> {
            String hangulWon = entity.getLimit().split(" ")[0];
            long supportAmount = Utils.convertCurrencyHangulToLong(hangulWon.trim());
            double averageRate = Utils.getAverageRate(Utils.convertRateStringToDouble(entity.getRate()));
            return new RecommendMunicipality(entity.getRegion(), supportAmount, averageRate);
        }).sorted(Comparator.comparing(RecommendMunicipality::getSupportAmount)
                .thenComparing(RecommendMunicipality::getAverageRate))
                .map(entity -> entity.getRegionName())
                .limit(count)
                .collect(Collectors.toList());

        return sortedRegionList;
    }

    public StringResponse getMinRateRegion() {
        Map<String, Double> regionMinRateMap = new LinkedHashMap<>();
        List<MunicipalityInfoEntity> entityList = municipalityRepository.findAll();

        /*
         * 정렬을 위한 데이터 셋팅
         * key : 지자체명(기관명)
         * value : 이차보전
         */
        for (MunicipalityInfoEntity entity : municipalityRepository.findAll()) {
            double minRate = Utils.convertRateStringToDouble(entity.getRate())[0];
            regionMinRateMap.put(entity.getRegion(), minRate);
        }

        // 이차보전 비율 기준 정렬
        String region = regionMinRateMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.naturalOrder()))
                .findFirst().get().getKey();

        return new StringResponse(region);
    }
}