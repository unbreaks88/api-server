package com.kakaopay.controller;

import com.kakaopay.dto.response.MunicipalityInfoResponse;
import com.kakaopay.dto.response.StringResponse;
import com.kakaopay.model.MunicipalityInfoEntity;
import com.kakaopay.service.MunicipalityService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.util.List;

@RestController
@RequestMapping("/v1/municipality")
public class MunicipalityController {

    @Autowired
    MunicipalityService municipalityService;


    // 1. 데이터 파일에서 각 레코드를 데이터베이스에 저장하는 API 개발
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file) {
        try {
            CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(new InputStreamReader(file.getInputStream(), "EUC-KR"));
            municipalityService.insertRows(parser.getRecords());
            return new ResponseEntity("Success", new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Fail", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 2. 지원하는 지자체 목록 검색 API
    @RequestMapping("/")
    public ResponseEntity<List<MunicipalityInfoResponse>> getMunicipalityList() {
        List<MunicipalityInfoResponse> responseBody = municipalityService.getMunicipalityList();
        return new ResponseEntity(responseBody, new HttpHeaders(), HttpStatus.OK);
    }

    // 3. 지원하는 지자체명을 입력 받아 해당 지자체의 지원정보를 출력하는 API
    @GetMapping("/{region}")
    public ResponseEntity<MunicipalityInfoResponse> getMunicipalityInfo(@PathVariable String region) {
        MunicipalityInfoResponse responseBody = municipalityService.findByRegion(region);
        return new ResponseEntity(responseBody, new HttpHeaders(), HttpStatus.OK);
    }

    // 4. 지원하는 지자체 정보 수정 기능 API 개발
    @PutMapping("/{region}")
    public MunicipalityInfoEntity updateMunicipality(@PathVariable String region, @RequestBody MunicipalityInfoEntity municipalityInfoEntity) {
        return municipalityService.updateMunicipalityInfo(region, municipalityInfoEntity);
    }

    // 5. 지원한도 컬럼에서 지원금액으로 내림차순 정렬(지원금액이 동일하면 이차보전 평균 비율이 적은 순서)하여 특정 개수만 출력하는 API
    // - 입력: 출력 개수 K
    // - 출력: K 개의 지자체명 (e.g. { 강릉시, 강원도, 거제시, 경기도, 경상남도 } )
    @GetMapping("/sort")
    public List<String> orderByRateDesc(@RequestParam final int count) {
        return municipalityService.orderByRateDesc(count);
    }

    // 6. 이차보전 컬럼에서 보전 비율이 가장 작은 추천 기관명을 출력하는 API
    @GetMapping("/recommend")
    public ResponseEntity<StringResponse> recommendMunicipality() {
        StringResponse responseBody = municipalityService.getMinRateRegion();
        return new ResponseEntity(responseBody, new HttpHeaders(), HttpStatus.OK);
    }

    // TODO 선택 문제
    @PostMapping("/recommend")
    public String recommendMunicipalityInfo() {
        return "";
    }
}
