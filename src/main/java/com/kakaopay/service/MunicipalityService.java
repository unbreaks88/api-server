package com.kakaopay.service;

import com.kakaopay.model.MunicipalityModel;
import com.kakaopay.repository.MunicipalityRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MunicipalityService {

    @Autowired
    private MunicipalityRepository municipalityRepository;

    public void insertRows(List<CSVRecord> records) {
        List<MunicipalityModel> recordList = new ArrayList<>();
        for (CSVRecord record : records) {
            recordList.add(new MunicipalityModel(record.get("지자체명(기관명)"), record.get("지원대상"), record.get("용도"), record.get("지원한도"), record.get("이차보전"), record.get("추천기관"), record.get("관리점"), record.get("취급점")));
        }
        municipalityRepository.saveAll(recordList);
    }

    public List<MunicipalityModel> getlocalGovList() {
        return municipalityRepository.findAll();
    }

    public MunicipalityModel findByRegion(final String region) {
        return municipalityRepository.findByRegion(region);
    }

    public MunicipalityModel updateModel(final String region, MunicipalityModel municipalityModel) {
        MunicipalityModel test = municipalityRepository.findByRegion(region);
        test.setRegion(municipalityModel.getRegion());
        test.setTarget(municipalityModel.getTarget());
        test.setUsage(municipalityModel.getUsage());
        test.set_limit(municipalityModel.get_limit());
        test.setRate(municipalityModel.getRate());
        test.setMgmt(municipalityModel.getMgmt());
        test.setReception(municipalityModel.getReception());

        municipalityRepository.save(test);
        return test;
    }
}
