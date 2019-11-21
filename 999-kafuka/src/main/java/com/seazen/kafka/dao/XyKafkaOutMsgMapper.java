package com.seazen.kafka.dao;

import com.seazen.kafka.bean.XyKafkaOutMsg;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface XyKafkaOutMsgMapper {

    int deleteByPrimaryKey(Long id);

    int insert(XyKafkaOutMsg record);

    int insertSelective(XyKafkaOutMsg record);

    XyKafkaOutMsg selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(XyKafkaOutMsg record);

    int updateByPrimaryKey(XyKafkaOutMsg record);

    //新增
    int updateByFwBh(@Param("outMsg") XyKafkaOutMsg outMsg);
}