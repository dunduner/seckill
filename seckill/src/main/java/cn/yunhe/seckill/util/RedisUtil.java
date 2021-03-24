package cn.yunhe.seckill.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by Administrator on 2018/3/30.
 */
public class RedisUtil {

    //Redis服务器IP
    private static String ADDR = null; //从redis.propterties获取

    //Redis的端口号
    private static int PORT = 0;//从redis.propterties获取

    //访问密码
    //private static String AUTH = null;
    private static String AUTH = null;//从redis.propterties获取

    //可用连接实例的最大数目，默认值为8；
    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private static int MAX_ACTIVE = 1024;

    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE = 200;

    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private static int MAX_WAIT = 10000;
    //链接超时时间
    private static int TIMEOUT = 10000;

    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = true;


    private static JedisPool jedisPool = null;

    /**
     * 初始化Redis连接池
     */
    static {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
//            config.setMaxActive(MAX_ACTIVE);--->maxActive 改成了MaxTotal()
            config.setMaxTotal(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
//            config.setMaxWait(MAX_WAIT);-----> setMaxWait() 改成了 setMaxWaitMillis()
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            Config config1 = new Config();
            //从redis.propterties获取
            ADDR = config1.getRedisConfigbyFile("redis.host");
            PORT = Integer.parseInt(config1.getRedisConfigbyFile("redis.port"));
            AUTH = config1.getRedisConfigbyFile("redis.auth");
            jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取jedis实例
     */
    public synchronized static Jedis getJedis() {
        try {
            if (jedisPool != null) {// jedisPool={JedisPool@5551}
                Jedis jedis = jedisPool.getResource();
                System.out.println("获取redis成功！");
                return jedis;// resource={Jedis@5622}
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 释放jedis资源
     */
    public static void returnResource(final Jedis jedis) {
        if (jedis != null) {
//            jedisPool.returnResource(jedis);
            jedisPool.close();
        }
    }


}