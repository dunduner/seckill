package cn.yunhe.seckill.dao.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import cn.yunhe.seckill.entity.Seckill;
import cn.yunhe.seckill.util.RedisUtil;
//缓存dao
public class RedisDao {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private JedisPool jedisPool;
	private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

	/**
	 * 传入id拿到seckill对象
	 * @param seckillId
	 * @return
	 */
	public Seckill getSeckill(int seckillId) {
		// redis 操作逻辑
		try {
			Jedis jedis = RedisUtil.getJedis();
			try {
				String key = "seckill:" + seckillId;
				// 并没有实现内部序列化操作
				// get -> byte[] -> 反序列化->Object(Seckill)  
				//java序列化速度和压缩比都不行，咱们用protostuff
				// 使用protostuff的前提 :必须是pojo
				byte[] bytes = jedis.get(key.getBytes());//key得到字节数组
				if (bytes != null) {
					// 用序列化框架转化
					Seckill seckill = schema.newMessage();
					//传递字节数组，上面的空对象，schema
					ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
					// seckill 就有了 return回去
					return seckill;
				}
			} finally {
				jedis.close();// 关闭redis连接池
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;//redis拿不到就返回null
	}
	/**
	 * 传入seckill
	 * @param seckill
	 * @return
	 */
	public String putSeckill(Seckill seckill) {
		// set Object(Seckill) ->序列化->byte[]
			Jedis jedis = RedisUtil.getJedis();
			try {
				String key = "seckill:" + seckill.getSeckillId();
				byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema,LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
				int timeout= 60*60;//设置时间   3600为一个小时
				//超时缓存  key  timeout  对象字节数组
				//将序列化号的对象放到redis中
				String result = jedis.setex(key.getBytes(), timeout,bytes);
				return result;//ok就是成功了
			} finally {
				jedis.close();
			}
	}
}