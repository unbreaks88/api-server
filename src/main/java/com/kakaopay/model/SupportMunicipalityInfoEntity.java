package com.kakaopay.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SUPPORT_TB")
// 지원 지자체(기관) : { 지자체명, 지자체 코드 }
public class SupportMunicipalityInfoEntity {
    @Id
    private String code;
    private String region;
}
