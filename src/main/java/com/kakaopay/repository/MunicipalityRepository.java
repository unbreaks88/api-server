package com.kakaopay.repository;

import com.kakaopay.model.MunicipalityModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MunicipalityRepository extends JpaRepository<MunicipalityModel, Long> {
    MunicipalityModel findByRegion(String region);
}
