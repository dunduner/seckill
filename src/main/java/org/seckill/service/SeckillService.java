package org.seckill.service;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;

import java.util.List;

/**
 * Created by Zhangning on 2017/3/21.
 */
public interface SeckillService {

    List<Seckill> getSeckillList();

    Seckill getById(int seckillId);

    Exposer exportSeckillUrl(int seckillid);

    SeckillExecution executeSeckill(int seckillId, long userPhone, String md5)
            throws SeckillException,RepeatKillException,SeckillCloseException;



}
