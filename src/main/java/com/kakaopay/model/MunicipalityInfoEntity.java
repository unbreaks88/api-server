package com.kakaopay.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Municipality_Info_TB")
//public class MunicipalityInfoEntity extends AbstractTimestampEntity {
public class MunicipalityInfoEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    private String region;
    private String target;
    private String usage;
    @Column(name = "lmit")
    private String limit;
    private String rate;
    private String institute;
    private String mgmt;
    private String reception;

    public MunicipalityInfoEntity(String region, String target, String usage, String limit, String rate, String institute, String mgmt, String reception) {
        this.region = region;
        this.target = target;
        this.usage = usage;
        this.limit = limit;
        this.rate = rate;
        this.institute = institute;
        this.mgmt = mgmt;
        this.reception = reception;
    }
}
