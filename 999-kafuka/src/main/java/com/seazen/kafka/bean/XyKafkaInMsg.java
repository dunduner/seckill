package com.seazen.kafka.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class XyKafkaInMsg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //档案号
    private Long fwBh;
    //创建时间
    private Date gmtCreate;
    //修改时间
    private Date gmtUpdate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFwBh() {
        return fwBh;
    }

    public void setFwBh(Long fwBh) {
        this.fwBh = fwBh;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtUpdate() {
        return gmtUpdate;
    }

    public void setGmtUpdate(Date gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }
}