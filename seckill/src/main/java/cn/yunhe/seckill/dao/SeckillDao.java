package cn.yunhe.seckill.dao;

import cn.yunhe.seckill.entity.Seckill;
import cn.yunhe.seckill.entity.Successseckilled;

import java.util.List;
import java.util.Map;



/**
 * 秒杀商品相关接口
 * @author Administrator
 */
public interface SeckillDao {

	/**
	 * 减库存
	 * @param seckillId
	 * @param killTime
	 * 秒杀的时间 如果影响行数大于1，标示更新的记录行数
	 */
//	int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);
	int reduceNumber(Successseckilled successseckilled);

	/**
	 * 根据id查询秒杀对象
	 * @param seckillId
	 */
	Seckill queryById(int seckillId);
	
	/**
	 * 查所有
	 * @return
	 */
	List<Seckill> queryAll();
	
	/**
	 * 调用存储过程 执行秒杀
	 * 咱们在存储过程中去回滚了
	 * 怎么在xml的mapper中使用呢？
	 */
	void killByProcedure(Map<String, Object> paramMap);

}
