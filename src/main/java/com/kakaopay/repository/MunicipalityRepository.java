package com.kakaopay.repository;

import com.kakaopay.model.MunicipalityInfoEntity;
import com.kakaopay.model.SupportMunicipalityInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MunicipalityRepository extends JpaRepository<MunicipalityInfoEntity, Long> {
    MunicipalityInfoEntity findBySupportInfoEntity(SupportMunicipalityInfoEntity supportMunicipalityInfoEntity);
}