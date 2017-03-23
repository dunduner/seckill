package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessKilled;

/**
 * Created by Zhangning on 2017/3/20.
 */
public interface SuccessKillDao {

    /**
     * 可过滤重复
     * @param seckillId
     * @param userPhone
     * @return  如果影响行数>1
     */
    int insertSuccessKilled(@Param("seckillId")int seckillId,@Param("userPhone") long userPhone);

    SuccessKilled queryByIdWithSeckill(@Param("seckillId")int seckillId,@Param("userPhone") long userPhone);


}
