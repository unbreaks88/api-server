package com.kakaopay.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "MUNICIPALITY_INFO_TB")
// 지자체 지원정보 엔티티 : { ID, 지자체 코드, 지원대상, 용도, 지원한도, 이차보전, 추천기관, 관리점, 취급점, 생성일자, 수정일자 }
public class MunicipalityInfoEntity extends AbstractTimestampEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne(cascade = {CascadeType.ALL})
    private SupportMunicipalityInfoEntity supportInfoEntity;
    private String target;
    private String usage;
    @Column(name = "lmit")
    private String limit;
    private String rate;
    private String institute;
    private String mgmt;
    private String reception;

    public MunicipalityInfoEntity(SupportMunicipalityInfoEntity supportInfoEntity, String target, String usage, String limit, String rate, String institute, String mgmt, String reception) {
        this.supportInfoEntity = supportInfoEntity;
        this.target = target;
        this.usage = usage;
        this.limit = limit;
        this.rate = rate;
        this.institute = institute;
        this.mgmt = mgmt;
        this.reception = reception;
    }
}
