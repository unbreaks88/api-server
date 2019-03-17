package com.kakaopay.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MunicipalityInfoRequest {
    private String region;
    private String target;
    private String usage;
    private String limit;
    private String rate;
    private String institute;
    private String mgmt;
    private String reception;
}