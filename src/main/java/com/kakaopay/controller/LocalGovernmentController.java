package com.kakaopay.controller;

import com.kakaopay.model.LocalGovernmentModel;
import com.kakaopay.service.LocalGovernmentService;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.util.List;

@RestController
@RequestMapping("/v1/api")
public class LocalGovernmentController {

    @Autowired
    LocalGovernmentService localGovernmentService;

    // 1. 데이터 파일에서 각 레코드를 데이터베이스에 저장하는 API 개발
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(new InputStreamReader(file.getInputStream(), "EUC-KR"));
            localGovernmentService.insertRows(parser.getRecords());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity("Success", new HttpHeaders(), HttpStatus.OK);
    }

    // 2. 지원하는 지자체 목록 검색 API 개발
    @RequestMapping("/list")
    public List<LocalGovernmentModel> getLocalGovernmentList() {
        return localGovernmentService.getlocalGovList();
    }

    // 3. 지원하는 지자체명을 입력 받아 해당 지자체의 지원정보를 출력하는 API 개발
    @RequestMapping("/get")
    public ResponseEntity<?> getLocalGovernment(final String region) {
        return new ResponseEntity("Success", new HttpHeaders(), HttpStatus.OK);
    }

    // 4. 지원하는 지자체 정보 수정 기능 API 개발
    @RequestMapping("/update")
    public ResponseEntity<?> updateLocalGovernment(final String region) {
        return new ResponseEntity("Success", new HttpHeaders(), HttpStatus.OK);
    }

    // 5. 지원한도 컬럼에서 지원금액으로 내림차순 정렬(지원금액이 동일하면 이차보전 평균 비율이 적은 순서)하여 특정 개수만 출력하는 API 개발
    // - 입력: 출력 개수 K
    // - 출력: K 개의 지자체명 (e.g. { 강릉시, 강원도, 거제시, 경기도, 경상남도 } )
    @RequestMapping("/order")
    public ResponseEntity<?> orderLocalGovernment(final String region) {
        return new ResponseEntity("Success", new HttpHeaders(), HttpStatus.OK);
    }

    // 6. 이차보전 컬럼에서 보전 비율이 가장 작은 추천 기관명을 출력하는 API 개발
    @RequestMapping("/recommend")
    public ResponseEntity<?> recommendLocalGovernment(final String region) {
        return new ResponseEntity("Success", new HttpHeaders(), HttpStatus.OK);
    }
}
