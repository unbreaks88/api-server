package com.kakaopay.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class AbstractTimestampEntity {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time", nullable = false)
    private Date created_time;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_time", nullable = false)
    private Date updated_time;

    @PrePersist
    protected void onCreate() {
        created_time = new Date();


        updated_time = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updated_time = new Date();
    }
}