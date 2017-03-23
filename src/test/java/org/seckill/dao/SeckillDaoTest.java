package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.seckill.entity.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Zhangning on 2017/3/21.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

    @Resource
    private SeckillDao seckillDao;

    /**
     *     //减库存
     * @throws Exception
     */
    @Test
    public void reduceNumber() throws Exception {
        int seckillId =5;
        Date killTime = new Date();
        int updateCount =seckillDao.reduceNumber(seckillId,killTime);
        Seckill s  =seckillDao.queryById(seckillId);
        if (updateCount>0)

            System.out.println(s.getName()+",秒杀成功");
    }

    @Test
    public void queryById() throws Exception {
        int id =1;
        Seckill s  =seckillDao.queryById(id);
        System.out.println(s.getName());
        System.out.println(s);
    }

    @Test
    public void queryAll() throws Exception {
        List<Seckill> Seckills = seckillDao.queryAll(0,5);
        for (Seckill seckill :Seckills){
            System.out.println(seckill);
        }
    }

}