package com.kakaopay.controller;

import com.kakaopay.dto.request.MunicipalityInfoRequest;
import com.kakaopay.dto.response.FileInfoResponse;
import com.kakaopay.dto.response.MunicipalityInfoResponse;
import com.kakaopay.dto.response.RegionInfoResponse;
import com.kakaopay.service.MunicipalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MunicipalityController {

    @Autowired
    MunicipalityService municipalityService;

    // 1. 데이터 파일에서 각 레코드를 데이터베이스에 저장하는 API
    @PostMapping("/municipality/upload")
    public FileInfoResponse uploadFile(@RequestParam MultipartFile file) {
        return municipalityService.insertRows(file);
    }

    // 2. 지원하는 지자체 목록 검색 API
    @RequestMapping("/municipality")
    public List<MunicipalityInfoResponse> getMunicipalityList() {
        return municipalityService.getMunicipalityList();
    }

    // 3. 지원하는 지자체명을 입력 받아 해당 지자체의 지원정보를 출력하는 API
    @GetMapping("/municipality/{region}")
    public MunicipalityInfoResponse getMunicipalityInfo(@PathVariable String region) {
        return municipalityService.findByRegion(region);
    }

    // 4. 지원하는 지자체 정보 수정 기능 API
    @PutMapping("/municipality")
    public MunicipalityInfoResponse updateMunicipality(@RequestBody MunicipalityInfoRequest municipalityInfoUpdateRequest) {
        return municipalityService.updateMunicipalityInfo(municipalityInfoUpdateRequest);
    }

    // 5. 지원한도 컬럼에서 지원금액으로 내림차순 정렬(지원금액이 동일하면 이차보전 평균 비율이 적은 순서)하여 특정 개수만 출력하는 API
    // - 입력: 출력 개수 K
    // - 출력: K 개의 지자체명 (e.g. { 강릉시, 강원도, 거제시, 경기도, 경상남도 } )
    @GetMapping("/municipality/sort/{count}")
    public RegionInfoResponse orderByRateDesc(@PathVariable final int count) {
        return municipalityService.orderByRateDesc(count);
    }

    // 6. 이차보전 컬럼에서 보전 비율이 가장 작은 추천 기관명을 출력하는 API
    @GetMapping("/municipality/recommend")
    public RegionInfoResponse recommendMunicipality() {
        return municipalityService.getMinRateRegion();
    }

//     TODO 선택 문제
//    @PostMapping("/municipality/recommend")
//    public RegionInfoResponse recommendMunicipalityInfo() {
//        return new RegionInfoResponse();
//    }
}
