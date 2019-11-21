package cn.yunhe.seckill.util;

import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

import cn.yunhe.seckill.entity.Seckill;
import redis.clients.jedis.Jedis;



public class ProtostuffUtil {
	
    private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<Class<?>, Schema<?>>();
    private static <T> Schema<T> getSchema(Class<T> clazz) {
        @SuppressWarnings("unchecked")
        Schema<T> schema = (Schema<T>) cachedSchema.get(clazz);
        if (schema == null) {
            schema = RuntimeSchema.getSchema(clazz);
            if (schema != null) {
                cachedSchema.put(clazz, schema);
            }
        }
        return schema;
    }

    /**
     * 序列化
     * @param obj
     * @return
     */
    public static <T> byte[] serializer(T obj) {
        @SuppressWarnings("unchecked")
        Class<T> clazz = (Class<T>) obj.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            Schema<T> schema = getSchema(clazz);
            return ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        } finally {
            buffer.clear();
        }
    }
    /**
     * 反序列化
     *
     * @param data
     * @param clazz
     * @return
     */
    public static <T> T deserializer(byte[] data, Class<T> clazz) {
        try {
            T obj = clazz.newInstance();
            Schema<T> schema = getSchema(clazz);
            ProtostuffIOUtil.mergeFrom(data, obj, schema);
            return obj;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }
    
    
    /** 产生一个随机的字符串 */
	public static String randomString(int length) {
		String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		int strlen = str.length();
		Random random = new Random();
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int num = random.nextInt(strlen);
			buf.append(str.charAt(num));
		}
		return buf.toString();
	}
	
	/**
	 * 测试方法
	 * @param args
	 */
    public static void main(String[] args) {
    	Seckill seckill = new Seckill(1l,"商品1",100,new Date(),new Date(),new Date());
    	byte[] bytes = ProtostuffUtil.serializer(seckill);
    	System.out.println(bytes);
    	Jedis jedis = RedisUtil.getJedis();
    	jedis.set("seckill:x".getBytes(), bytes);
    	
    	Seckill seckillFanxuliehua = ProtostuffUtil.deserializer(bytes, Seckill.class);
    	System.out.println(seckillFanxuliehua);
	}
}