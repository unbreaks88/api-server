package com.kakaopay.service;

import com.kakaopay.model.LocalGovernmentModel;
import com.kakaopay.repository.LocalGovernmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class LocalGovernmentService {

    @Autowired
    private LocalGovernmentRepository localGovernmentRepository;

    public void insertRows(List<CSVRecord> records) {
        List<LocalGovernmentModel> recordList = new ArrayList<>();
        for (CSVRecord record : records) {
            recordList.add(new LocalGovernmentModel(Long.parseLong(record.get("구분")), record.get("지자체명(기관명)"), record.get("지원대상"), record.get("용도"), record.get("지원한도"), record.get("이차보전"), record.get("추천기관"), record.get("관리점"), record.get("취급점")));
        }
        localGovernmentRepository.saveAll(recordList);
    }

    public List<LocalGovernmentModel> getlocalGovList() {
        return localGovernmentRepository.findAll();
    }
}
