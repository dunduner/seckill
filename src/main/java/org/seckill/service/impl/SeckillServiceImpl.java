package org.seckill.service.impl;

import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKillDao;
import org.seckill.dao.cache.RedisPool;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SeckillServiceImpl implements SeckillService{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "successKillDao")
    private SuccessKillDao successKillDao;

    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private RedisPool redisPool;

    private  final  String slat =";asjd;lasjijwj((&^dlsajdoiqwjj&^&*Y";

    public List<Seckill> getSeckillList(){
        return seckillDao.queryAll(0,10);
    }

    //md5 混淆
    public Seckill getById(int seckillId) {
        return seckillDao.queryById(seckillId);
    }

    /**
     * 暴露秒杀地址
     * @param seckillid
     * @return
     */
    public Exposer exportSeckillUrl(int seckillid) {
        //优化点  缓存优化
        //1.redis缓存中拿这个对象
        Seckill seckill= redisPool.getSeckill(seckillid);
        //2.如果没有 就访问数据拿这个对象
        if(seckill ==null){
            seckill = seckillDao.queryById(seckillid);

            if(seckill == null){
                return new Exposer(false,seckillid);
            }else {
                //3.放入redis
                redisPool.putSeckill(seckill);
            }
        }
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date nowTime = new Date();
        if (nowTime.getTime() <startTime.getTime() ||nowTime.getTime() >endTime.getTime()) {
            return new Exposer(false,seckillid,nowTime.getTime(),startTime.getTime(),endTime.getTime());
        }
        String md5 = getMD5(seckillid);
        return new Exposer(true,md5,seckillid);
    }

    /**
     * 执行秒杀
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     * @throws SeckillException
     * @throws RepeatKillException
     * @throws SeckillCloseException
     */
    @Transactional
    public SeckillExecution executeSeckill(int seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        if(md5 == null ||!md5.equals(getMD5(seckillId))){
            throw new SeckillException("数据篡改");
        }
        Date nowTime = new Date();
        try {
            //record purchase message
            int insertCount = successKillDao.insertSuccessKilled(seckillId, userPhone);
            if(insertCount<=0){
                //repeat seckill
                throw new RepeatKillException("重复秒杀");
            }
            else{
                //execute seckill:1.reduce product 2.record purchase message    //热点商品竞争
                int updateCount = seckillDao.reduceNumber(seckillId,nowTime);
                //do not update for record
                if(updateCount<=0){
                    throw new SeckillCloseException("秒杀关闭！");
                }
                else{
                    //秒杀成功
                    SuccessKilled successKilled = successKillDao.queryByIdWithSeckill(seckillId,userPhone);
                    return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS,successKilled);
                }

            }
        }catch (SeckillCloseException e1){
            throw e1;
        }catch (RepeatKillException e2){
            throw e2;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            //所有编译期异常  转化为运行期异常  spring 声明式事务  会帮我们做rollback
            throw new SeckillException(e.getMessage());
        }
    }


    private  String getMD5 (int seckillid){
        String base =seckillid +"/"+slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return  md5;
    }
}
