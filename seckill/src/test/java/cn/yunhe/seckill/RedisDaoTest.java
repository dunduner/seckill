package cn.yunhe.seckill;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.yunhe.seckill.dao.SeckillDao;
import cn.yunhe.seckill.dao.cache.RedisDao;
import cn.yunhe.seckill.entity.Seckill;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDaoTest{
	@Autowired
	private RedisDao redisDao;
	@Autowired
	private SeckillDao seckillDao;
	@Test
	public void testSckill(){
		//get and put 
		int seckillId = 2;
		Seckill seckill = redisDao.getSeckill(seckillId);//根据id去数据库拿
		System.out.println("seckillId="+seckillId+""+seckill);
		if (seckill ==null) {
			seckill = seckillDao.queryById(seckillId);//根据id去数据库中拿
			if (seckill!=null) {
				String result =redisDao.putSeckill(seckill);
				System.out.println("redis是否放入成功："+result);
				seckill = redisDao.getSeckill(seckillId);
				System.out.println("从reids中获取的seckill："+seckill);
			}
		}
	}
}