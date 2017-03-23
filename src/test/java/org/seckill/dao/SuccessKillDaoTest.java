package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by Zhangning on 2017/3/21.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKillDaoTest {
    @Autowired
    private SuccessKillDao successKillDao;
    @Test
    public void insertSuccessKilled() throws Exception {
    int id =5;
    long phone = 18812345678L;
       int insertNum =  successKillDao.insertSuccessKilled(id,phone);
        System.out.println("insertNum:"+insertNum);
    }

    @Test
    public void queryByIdWithSeckill() throws Exception {
        int id =5;
        long phone = 18812345678L;
        SuccessKilled successKilled = successKillDao.queryByIdWithSeckill(id,phone);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());
        /*
        * SuccessKilled{seckillId=5, userPhone=18812345678, state=0, createTime=Tue Mar 21 10:47:48 CST 2017, seckill=seckill{seckillId=5, name='588秒杀化妆品', number=886, startTime=Mon Mar 20 11:01:14 CST 2017, endTime=Sat Apr 01 11:01:18 CST 2017, createTime=Mon Mar 20 11:02:17 CST 2017}}
seckill{seckillId=5, name='588秒杀化妆品', number=886, startTime=Mon Mar 20 11:01:14 CST 2017, endTime=Sat Apr 01 11:01:18 CST 2017, createTime=Mon Mar 20 11:02:17 CST 2017}
        *
        * */
    }

}