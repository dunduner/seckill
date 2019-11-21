package cn.yunhe.seckill;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.yunhe.seckill.dao.SuccessseckilledDao;
import cn.yunhe.seckill.entity.Successseckilled;

@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledlDaoTest {

	
	@Resource(name="successseckilledDao")
	private SuccessseckilledDao successseckilledDao;
	
	
	
	/**
	 * 插入购买明细，可过滤重复
	 * @throws Exception
	 */
	@Test
	public void InsertSuccessKilled() throws Exception{
		int id = 2;
		Long phone =13623865089l;
		int insertCount = successseckilledDao.insertSuccessKilled(id, phone);
		System.out.println("insertCount:"+insertCount);
		if(insertCount>0){
			
		}else{
			
		}
	}
	
	@Test
	public void testQueryByIdWithSeckill() throws Exception{
		int id = 2;
		long phone =13623865089L;
		Successseckilled successKilled = successseckilledDao.queryByIdWithSeckill(id, phone);
		System.out.println(successKilled);
		System.out.println(successKilled.getSeckill());
	}
}
