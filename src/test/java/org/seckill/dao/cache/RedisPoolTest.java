package org.seckill.dao.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dao.SeckillDao;
import org.seckill.entity.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by Zhangning on 2017/3/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisPoolTest {
    private  int seckillId = 5;
    @Autowired
    private  RedisPool redisPool;
    @Autowired
    private SeckillDao seckillDao;
    @Test
    public void testSeckill() throws Exception {
            //get  and put
        Seckill seckill = redisPool.getSeckill(seckillId);
        System.out.println("缓存中拿到的seckill："+seckill);
        if(seckill==null){
            seckill = seckillDao.queryById(seckillId);
            System.out.println("数据库中拿到的seckill："+seckill);
            if(seckill!=null){
              String result =   redisPool.putSeckill(seckill);
                System.out.println(result);
            }
        }
    }


}