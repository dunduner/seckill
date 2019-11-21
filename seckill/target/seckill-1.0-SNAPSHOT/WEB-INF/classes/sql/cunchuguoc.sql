delimiter $$   -- 将换行符分号 改为$$
-- 定义存储过程
-- 参数 in 输入参数   out输出参数，不能用，但是可以赋值

-- SUCCESS(1,"秒杀成功"),END(0,"秒杀结束") REPEAT_KILL(-1,"重复秒杀"),INNER_ERROR(-2,"系统异常"), DATA_REWRITE(-3,"数据篡改");
CREATE PROCEDURE `seckill`.`execute_seckill`
  (in v_seckill_id bigint,in v_phone bigint,in v_kill_time timestamp,out r_result int)
  BEGIN-- 开始存储过程
    DECLARE insert_count int DEFAULT 0;-- 定义的一个变量
    START TRANSACTION;
		
    insert ignore into success_killed(seckill_id,user_phone,create_time)  values (v_seckill_id,v_phone,v_kill_time);  -- 插入购买行为语句结束
    select row_count() into insert_count; -- 将影响的行数放到 自定义的 insert_count中 
	-- row_count() 返回上一条修改类型（delete insert update）的行数
	-- row_count: 0:未修改数据; >0:表示修改的行数; <0:sql错误/未执行修改sql
    IF (insert_count = 0) THEN  
      ROLLBACK;
      set r_result = -1;-- 重复秒杀
    ELSEIF(insert_count < 0) THEN
      ROLLBACK;
      SET R_RESULT = -2; -- 系统异常
    ELSE  -- 插入购买秒杀逻辑成功
      update seckill set number = number-1  where seckill_id = v_seckill_id and end_time > v_kill_time and start_time < v_kill_time  and number > 0;
      -- 减库存代码结束
      select row_count() into insert_count;
      IF (insert_count = 0) THEN
        ROLLBACK;
        set r_result = 0; -- 秒杀结束
      ELSEIF (insert_count < 0) THEN
        ROLLBACK;
        set r_result = -2;-- 系统异常
      ELSE
        COMMIT;
        set r_result = 1; -- 秒杀成功
      END IF;
    END IF;
  END; -- BEGIN的end
$$
-- 存储过程定义结束
DELIMITER ; -- 将$$ 转为 ;




-- 定义变量
set @r_result=-3;
-- 执行存储过程
call execute_seckill(1,13502178891,now(),@r_result);
-- 获取结果
select @r_result;

-- 存储过程
-- 1:存储过程优化：事务行级锁持有的时间
-- 2:不要过度依赖存储过程
-- 3:简单的逻辑可以应用存储过程
-- 4:QPS:一个秒杀单6000/qps