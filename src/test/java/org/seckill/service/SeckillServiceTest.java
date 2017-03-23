package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Zhangning on 2017/3/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;
    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> list = seckillService.getSeckillList();
        logger.info("list={}",list);
    }

    @Test
    public void getById() throws Exception {
        int SeckillId = 5;
        Seckill seckill  = seckillService.getById(SeckillId);
        logger.info("seckill={}",seckill);
    }

    @Test
    public void exportSeckillUrl() throws Exception {
        int SeckillId = 5;
        long phone = 13623865012L;
        Exposer exposer = seckillService.exportSeckillUrl(SeckillId);
        logger.info("exposer={}",exposer);
        /*
        *  exposer=Exposer{pxposed=true,
        *  md5='200dc3aef07dd00b4e91555aea582d5a',
        *  seckillId=5, now=0, start=0, end=0}
        * */
    }

    @Test
    public void executeSeckill() throws Exception {
        int SeckillId = 5;
        long phone = 18823865222L;
        String md5 = "200dc3aef07dd00b4e91555aea582d5a";
        try{
            SeckillExecution seckillExecution = seckillService.executeSeckill(SeckillId,phone,md5);
            logger.info("seckillExecution={}",seckillExecution);
        }catch (RepeatKillException e){
            logger.error(e.getMessage());
        }catch (SeckillCloseException e){
            logger.error(e.getMessage());
        }

    }
}