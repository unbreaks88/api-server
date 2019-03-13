package com.kakaopay.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class LocalGovernmentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long _id;
    private String _region;
    private String _target;
    private String _usage;
    private String _limit;
    private String _rate;
    private String _institute;
    private String _mgmt;
    private String _reception;


//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//    @NonNull
//    @Column(unique = true)
//    private String email;
//    @NonNull
//    @Column(unique = true)
//    private String code;
////    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
////    private Date createdAt;
}
