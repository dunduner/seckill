package org.seckill.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.seckill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisPool {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private JedisPool jedisPool;

    public  RedisPool (String ip,int port){
        //构造方法注入
        jedisPool = new JedisPool(ip,port);
    }

    private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);


    public Seckill getSeckill(int seckillId){
        try {
            //拿到redis连接
            Jedis jedis = jedisPool.getResource();
            try{
                String key ="seckill:"+seckillId;
                //序列化
                byte[] bytes =  jedis.get(key.getBytes());
                if(bytes!=null){
                    //空对象  seckill
                    Seckill seckill = schema.newMessage();
                    //用ProtostuffIOUtil 给空对象seckill赋值
                    ProtostuffIOUtil.mergeFrom(bytes,seckill,schema);
                    return  seckill;
                }
            }finally {
                jedis.close();
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    /**
     * 将seckill对象序列化 并放入  redis缓存中
     * @param seckill
     * @return
     */
    public String putSeckill(Seckill seckill){
        try{
            Jedis jedis = jedisPool.getResource();
            try{
                String key = "seckill:"+seckill.getSeckillId();
                byte[]  bytes = ProtostuffIOUtil.toByteArray(seckill,schema,
                        LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                //缓存1小时
                int timeout = 60*60;
                String result = jedis.setex(key.getBytes(),timeout,bytes);
                return  result;
            }finally {
                jedis.close();
            }

        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return null;
    }
}
