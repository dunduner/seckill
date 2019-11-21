package com.seazen.kafka.dao;


import com.seazen.kafka.bean.XyKafkaInMsg;

public interface XyKafkaInMsgMapper {
    int deleteByPrimaryKey(Long id);

    int insert(XyKafkaInMsg record);

    int insertSelective(XyKafkaInMsg record);

    XyKafkaInMsg selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(XyKafkaInMsg record);

    int updateByPrimaryKey(XyKafkaInMsg record);

    XyKafkaInMsg selectByFuBh(Long fwBh);
}