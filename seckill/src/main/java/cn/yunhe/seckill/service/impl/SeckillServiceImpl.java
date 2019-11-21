package cn.yunhe.seckill.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PathVariable;

import cn.yunhe.seckill.dao.SeckillDao;
import cn.yunhe.seckill.dao.SuccessseckilledDao;
import cn.yunhe.seckill.dao.cache.RedisDao;
import cn.yunhe.seckill.dto.Exposer;
import cn.yunhe.seckill.dto.SeckillExecution;
import cn.yunhe.seckill.entity.Seckill;
import cn.yunhe.seckill.entity.Successseckilled;
import cn.yunhe.seckill.enumpack.SeckillStateEnum;
import cn.yunhe.seckill.exception.RepeatKillException;
import cn.yunhe.seckill.exception.SeckillCloseException;
import cn.yunhe.seckill.exception.SeckillException;
import cn.yunhe.seckill.service.SeckillService;
import cn.yunhe.seckill.util.ProtostuffUtil;
import cn.yunhe.seckill.util.RedisUtil;
import cn.yunhe.seckill.util.SerializeUtil;
import redis.clients.jedis.Jedis;

@Service
public class SeckillServiceImpl implements SeckillService {
	// 日志的加载
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private final String slat = "lkasjd901@82oi()&^dpuj98aj@12o3uksjd@#(*l";

	// 注入你的dao接口
	@Autowired
	private SeckillDao seckillDao;
	@Autowired
	private SuccessseckilledDao successseckilledDao;
	@Autowired
	private RedisDao redisDao;
	/**
	 * 查询所有
	 */
	@Override
	public List<Seckill> getSeckillList() {
		List<Seckill> seckillList = seckillDao.queryAll();
		return seckillList;
	}

	/**
	 * 根据id查询单个对象
	 */
	@Override
	public Seckill getById(int seckillId) {
		Seckill seckillOne = seckillDao.queryById(seckillId);
		return seckillOne;
	}

	/**
	 * 暴露秒杀地址 秒杀开启时候 。输出秒杀接口的地址 否则输出系统时间和秒杀时间
	 */
	@Override
	public Exposer exportSeckillUrl(int seckillId) {
		Jedis jedis = RedisUtil.getJedis();
		String key = "seckill:"+seckillId;
		Seckill seckill;
		//请求的时候先去redis里面拿，拿不到，再去Mysql中拿，拿了以后，存入reids，并返回给客户
		byte[] seckillValueByte = jedis.get(key.getBytes());
//		seckill = (Seckill) SerializeUtil.unserialize(bs);
		if(seckillValueByte!=null){
			//如果redis中有
			seckill = ProtostuffUtil.deserializer(seckillValueByte, Seckill.class);
		}else{
			//redis没有
			seckill = seckillDao.queryById(seckillId);//数据库拿
			if(seckill==null){
				return new Exposer(false, seckillId);
			}else{
				//数据库里面有这个商品。要往redis里面放
				byte[] serializer = ProtostuffUtil.serializer(seckill);
//				byte[] serialize = SerializeUtil.serialize(seckill);
				jedis.setex(key.getBytes(),18000,serializer);
//				jedis.set(key.getBytes(), serializer);
			}
		}
		// 如果有这个商品 拿到系统时间 秒杀的开始和结束时间
		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		Date newTime = new Date();
		// 情况2.没有开始秒杀 或者秒杀结束
		if (newTime.getTime() < startTime.getTime() || newTime.getTime() > endTime.getTime()) {
			return new Exposer(false, seckillId, newTime.getTime(), startTime.getTime(), endTime.getTime());
		}
		// 情况3 系统时间再秒杀开始和结束直接按 ，可以暴漏秒杀地址
		String md5 = getMD5(seckillId);
		return new Exposer(true, md5, seckillId);
	}

	/**
	 * 传入秒杀商品id得到加密的md5---私有方法 不愿意被别人访问到
	 * 
	 * @param seckillId
	 * @return
	 */
	private String getMD5(long seckillId) {
		String base = seckillId + "/" + slat;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}

	/**
	 * 执行秒杀操作 使用注解控制事务方法的要点 1.团队一看就知道你开启了事务,看到注解就知道 这个方法是事务方法
	 * 2.保证事务方法的执行时间尽量短，不要穿插其他的网络操作 ，减少事务的持有时间
	 * 3.不是所有的方法都需要事务，如果只有一条修改操作或者只读操作不需要事务控制
	 */
	@Override
	@Transactional
	public SeckillExecution executeSeckill(int seckillId,long userPhone, String md5)
			throws SeckillCloseException, SeckillException, RepeatKillException {
		System.out.println(md5);
		// 1、判断md5是否正确
		if (md5 == null || !md5.equals(getMD5(seckillId))) {
			throw new SeckillException("秒杀接口不对！");// 事务就rollback
		}
		// 2、执行秒杀逻辑 减库存和记录购买行为
		try {
			int insertSuccessKilled = successseckilledDao.insertSuccessKilled(seckillId, userPhone);
			if (insertSuccessKilled <= 0) {
				// 没有插入购买记录成功。重复秒杀
				throw new RepeatKillException("重复秒杀");
			} else {
				// 插入购买行为成功，也就创建订单成功了
				Successseckilled successseckilled = new Successseckilled();
				successseckilled.setCreateTime(new Date());
				successseckilled.setSeckillId(seckillId);
				//热点商品竞争！
				int reduceNumber = seckillDao.reduceNumber(successseckilled);
				if (reduceNumber <= 0) {
					throw new SeckillCloseException("秒杀结束或者商品没有了");
				} else {
					// 秒杀成功 commit
					// 返回订单的实体类
					Successseckilled successKilled = successseckilledDao.queryByIdWithSeckill(seckillId, userPhone);
					return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
				}

			}
		} catch (SeckillCloseException seckillCloseException) {
			throw seckillCloseException;// 在捕获的地方再次抛出，就可以解决事务不回滚的问题
		} catch (RepeatKillException e2) {
			throw e2;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			// 所有编译的异常 转化为运行期异常-如数据库断了等等
			throw new SeckillException("seckill inner error:" + e.getMessage());
		}

	}
	
	
	/**
	 * 调用存储过程 执行秒杀
	 * 咱们在存储过程中去回滚了
	 */
	@Override
	public SeckillExecution executeSeckillByProcedure(int seckillId, long userPhone, String md5) {
		// 1、判断md5是否正确
		if (md5 == null || !md5.equals(getMD5(seckillId))) {
			throw new SeckillException("秒杀接口不对！");// 事务就rollback
		}
		Date killTime = new Date();
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("seckillId", seckillId);
		map.put("phone", userPhone);
		map.put("killTime", killTime);
		map.put("result", null);

		try {
			seckillDao.killByProcedure(map);//resulut执行后会被赋值
			//拿map中的resulut属性，没有给默认值-2
			int  result = MapUtils.getInteger(map, "result", -2);
			System.out.println(result);
			
			
			if(result== 1){
				Successseckilled queryByIdWithSeckill = successseckilledDao.queryByIdWithSeckill(seckillId, userPhone);
				return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, queryByIdWithSeckill);
			}else{
				return new SeckillExecution(seckillId, SeckillStateEnum.stateOf(result));//-1  -2  0 
			}
		} catch (Exception e) {
			return new SeckillExecution(seckillId, SeckillStateEnum.INNER_ERROR);
		}
	}

}
