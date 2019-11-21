package cn.yunhe.seckill.service;

import java.util.List;

import cn.yunhe.seckill.dto.Exposer;
import cn.yunhe.seckill.dto.SeckillExecution;
import cn.yunhe.seckill.entity.Seckill;
import cn.yunhe.seckill.exception.RepeatKillException;
import cn.yunhe.seckill.exception.SeckillCloseException;
import cn.yunhe.seckill.exception.SeckillException;

/**
 * 站在使用者角度设计接口
 * 三个方面：方法定义名字，参数，返回类型/异常
 * @author Zhangning
 */
public interface SeckillService {
	/**
	 * 查询所有参与秒杀的商品记录
	 * @return
	 */
	List<Seckill> getSeckillList();
	/**
	 * 查询单个秒杀记录
	 * @param seckillId
	 * @return
	 */
	Seckill getById(int seckillId);
	/**
	 * 秒杀开启时候 。输出秒杀接口的地址
	 * 否则输出系统时间和秒杀时间
	 * @param seckillId
	 */
	Exposer exportSeckillUrl(int seckillId);
	/**
	 * 用户点击秒杀按钮 去执行秒杀操作
	 * @param seckillId
	 * @param userPhone
      * spring声明式事务  需要抛出运行时异常方可rollback
	 * @param md5  返回类型需要创建一个DTO数据封装，并定义一下自定义的异常类
	 */
	SeckillExecution executeSeckill(int seckillId, long userPhone, String md5) throws SeckillCloseException,SeckillException,RepeatKillException;
	
	/**
	 * 调用存储过程 执行秒杀
	 * 咱们在存储过程中去回滚了
	 */
	SeckillExecution executeSeckillByProcedure(int seckillId, long userPhone, String md5);
}
