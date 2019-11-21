package cn.yunhe.seckill;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.yunhe.seckill.dao.SeckillDao;
import cn.yunhe.seckill.entity.Seckill;
import cn.yunhe.seckill.entity.Successseckilled;

@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {
	
	@Autowired
	private SeckillDao seckillDao;
	
	/**
	 * 根据id查询单个对象
	 * @throws Exception
	 */
	@Test
	public void testQueryById() throws Exception{
		int id = 1;
		Seckill seckill = seckillDao.queryById(id);
		System.out.println(seckill.getName());
		System.out.println(seckill);
	}

	/**
	 * 减库存接口
	 * @throws Exception
	 */
	@Test
	public void testReduceNumber() throws Exception{
		Successseckilled successseckilled =  new Successseckilled();
		Date killTime = new Date();
		successseckilled.setCreateTime(killTime);
		successseckilled.setSeckillId(2);
		int updateCount = seckillDao.reduceNumber(successseckilled);
		System.out.println("updateCount:"+updateCount);
		if(updateCount>0){
			System.out.println("减库存成功");
		}else{
			System.out.println("秒杀失败!!!");
		}
	}
}
