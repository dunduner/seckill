package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Seckill;

import java.util.Date;
import java.util.List;

/**s
 * Created by Zhangning on 2017/3/20.
 */
public interface SeckillDao {


    //减库存
    int reduceNumber(@Param("seckillId")int seckillId,@Param("killTime") Date killTime);

    Seckill queryById(@Param("seckillId") int seckillId);


    List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);

}
