package com.kakaopay.repository;

import com.kakaopay.model.MunicipalityInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MunicipalityRepository extends JpaRepository<MunicipalityInfoEntity, Long> {
    MunicipalityInfoEntity findByRegion(String region);
}
