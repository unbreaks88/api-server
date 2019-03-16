package com.kakaopay.repository;

import com.kakaopay.model.SupportMunicipalityInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SupportMunicipalityRepository extends JpaRepository<SupportMunicipalityInfoEntity, String> {
    SupportMunicipalityInfoEntity findByRegion(String region);
}
