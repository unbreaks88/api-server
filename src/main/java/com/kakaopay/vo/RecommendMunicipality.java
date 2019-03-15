package com.kakaopay.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecommendMunicipality {
    private String regionName;
    private long supportAmount;
    private double averageRate;
}
