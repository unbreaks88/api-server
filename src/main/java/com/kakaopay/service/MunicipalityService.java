package com.kakaopay.service;

import com.kakaopay.dto.request.MunicipalityInfoRequest;
import com.kakaopay.dto.response.FileUploadResponse;
import com.kakaopay.dto.response.MinRateRegionResponse;
import com.kakaopay.dto.response.MunicipalityInfoResponse;
import com.kakaopay.dto.response.TopNResponse;
import com.kakaopay.model.MunicipalityInfoEntity;
import com.kakaopay.model.SupportMunicipalityInfoEntity;
import com.kakaopay.repository.MunicipalityRepository;
import com.kakaopay.repository.SupportMunicipalityRepository;
import com.kakaopay.util.Utils;
import com.kakaopay.vo.RecommendMunicipality;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
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

    @Autowired
    private SupportMunicipalityRepository supportMunicipalityRepository;

    // FIXME Bulk insert 고민
    public FileUploadResponse insertRows(final MultipartFile file) {
        List<MunicipalityInfoEntity> recordList = new ArrayList<>();
        String msg = "SUCCESS";
        try {
            CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(new InputStreamReader(file.getInputStream(), "EUC-KR"));
            int i = 0;
            for (CSVRecord record : parser.getRecords()) {
                StringBuilder locationCode = new StringBuilder("LC");
                locationCode.append(String.valueOf(i++));
                SupportMunicipalityInfoEntity supportInfoEntity = new SupportMunicipalityInfoEntity(locationCode.toString(), record.get("지자체명(기관명)"));
                recordList.add(new MunicipalityInfoEntity(supportInfoEntity, record.get("지원대상"), record.get("용도"), record.get("지원한도"), record.get("이차보전"), record.get("추천기관"), record.get("관리점"), record.get("취급점")));
            }
            municipalityRepository.saveAll(recordList);
        } catch (IOException e) {
            msg = e.getCause().getMessage();
        }
        return new FileUploadResponse(file.getOriginalFilename(), file.getSize(), recordList.size(), msg);
    }

    public List<MunicipalityInfoResponse> getMunicipalityList() {
        // Entity --> Reponse 변환
        List<MunicipalityInfoResponse> responses = municipalityRepository.findAll()
                .stream()
                .map(entity -> {
                    return new MunicipalityInfoResponse(
                            entity.getSupportInfoEntity().getRegion(), entity.getTarget(), entity.getUsage(), entity.getLimit(), entity.getRate(), entity.getInstitute(), entity.getMgmt(), entity.getReception()
                    );
                }).collect(Collectors.toList());
        return responses;
    }

    public MunicipalityInfoResponse findByRegion(final String region) {
        SupportMunicipalityInfoEntity supportEntity = supportMunicipalityRepository.findByRegion(region).orElse(null);
        if (supportEntity != null) {
            MunicipalityInfoEntity entity = municipalityRepository.findBySupportInfoEntity(supportEntity);
            return new MunicipalityInfoResponse(entity.getSupportInfoEntity().getRegion(), entity.getTarget(), entity.getUsage(), entity.getLimit(), entity.getRate(), entity.getInstitute(), entity.getMgmt(), entity.getReception());
        }
        // FIXME
        return null;
    }

    public MunicipalityInfoResponse updateMunicipalityInfo(final String region, MunicipalityInfoRequest updateRequest) {
        SupportMunicipalityInfoEntity supportEntity = supportMunicipalityRepository.findByRegion(region).orElse(null);
        if (supportEntity != null) {
            MunicipalityInfoEntity entity = municipalityRepository.findBySupportInfoEntity(supportEntity);

            entity.getSupportInfoEntity().setRegion(updateRequest.getRegion());
            entity.setTarget(updateRequest.getTarget());
            entity.setUsage(updateRequest.getUsage());
            entity.setLimit(updateRequest.getLimit());
            entity.setRate(updateRequest.getRate());
            entity.setMgmt(updateRequest.getMgmt());
            entity.setReception(updateRequest.getReception());

            municipalityRepository.save(entity);
            return new MunicipalityInfoResponse(entity.getSupportInfoEntity().getRegion(), entity.getTarget(), entity.getUsage(), entity.getLimit(), entity.getRate(), entity.getInstitute(), entity.getMgmt(), entity.getReception());
        }
        // FIXME
        return null;
    }

    public TopNResponse orderByRateDesc(final int count) {
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
            return new RecommendMunicipality(entity.getSupportInfoEntity().getRegion(), supportAmount, averageRate);
        }).sorted(Comparator.comparing(RecommendMunicipality::getSupportAmount).reversed()
                .thenComparing(RecommendMunicipality::getAverageRate))
                .map(entity -> entity.getRegionName())
                .limit(count)
                .collect(Collectors.toList());

        return new TopNResponse(sortedRegionList);
    }

    public MinRateRegionResponse getMinRateRegion() {
        Map<String, Double> regionMinRateMap = new LinkedHashMap<>();

        /*
         * 정렬을 위한 데이터 셋팅
         * key : 지자체명(기관명)
         * region : 이차보전
         */
        for (MunicipalityInfoEntity entity : municipalityRepository.findAll()) {
            double minRate = Utils.convertRateStringToDouble(entity.getRate())[0];
            regionMinRateMap.put(entity.getSupportInfoEntity().getRegion(), minRate);
        }

        // 이차보전 기준 정렬
        String region = regionMinRateMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.naturalOrder()))
                .findFirst().get().getKey();

        return new MinRateRegionResponse(region);
    }
}