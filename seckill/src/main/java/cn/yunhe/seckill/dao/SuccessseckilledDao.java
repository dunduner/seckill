package cn.yunhe.seckill.dao;

import org.apache.ibatis.annotations.Param;

import cn.yunhe.seckill.entity.Successseckilled;

/**
 * 订单接口
 * @author Administrator
 *
 */
public interface SuccessseckilledDao {
	/**
	 * 插入购买明细，可过滤重复
	 * @param seckillId
	 * @param userPhone
	 */
	int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);
	/**
	 * 根据id和手机号查询SuccessKilled并携带秒杀产品对象实体
	 * @param seckillId
	 */
	Successseckilled queryByIdWithSeckill(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);
}
